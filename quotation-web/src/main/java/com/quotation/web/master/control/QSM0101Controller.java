package com.quotation.web.master.control;

import com.quotation.common.consts.CodeConst;
import com.quotation.core.base.BaseFileController;
import com.quotation.core.bean.BaseParam;
import com.quotation.core.exception.BusinessException;
import com.quotation.core.util.StringUtil;
import com.quotation.web.core.control.BaseResponse;
import com.quotation.web.master.entity.TnmSystemMasterEntity;
import com.quotation.web.master.service.TnmSystemMasterService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * Created by yang_shoulai on 8/4/2017.
 */
@Controller
@RequestMapping(value = "/QSM0101")
public class QSM0101Controller extends BaseFileController {

    private static final Logger log = LoggerFactory.getLogger(QSM0101Controller.class);

    private static final String PREFIX = "SystemMaster_";

    private static final String PREFIX_VIEW_ONLY = "SystemMaster_ViewOnly_";

    private static final String PREFIX_NOT_VIEW_ONLY = "SystemMaster_NotViewOnly_";

    private static final String DATE_FORMAT = "yyyyMMddhhmmss";

    private final TnmSystemMasterService systemMasterService;

    @Autowired
    public QSM0101Controller(TnmSystemMasterService systemMasterService) {
        this.systemMasterService = systemMasterService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        BaseParam param = new BaseParam();
        setCommonParam(param, request);
        List<TnmSystemMasterEntity> entities = systemMasterService.listAll(param, null);
        model.addAttribute("masterList", entities);
        return "master/QSM0101";
    }

    @RequestMapping(value = "/uploadPopup")
    public String uploadPopup(@RequestParam(value = "masterIds", required = false, defaultValue = "") String masterIdStr, Model model, HttpServletRequest request) {
        model.addAttribute("masterIdStr", masterIdStr);
        return "master/QFM0112";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(@RequestParam(value = "masterIds", required = false, defaultValue = "") String masterIdStr, HttpServletRequest request, HttpServletResponse response) {
        BaseParam param = new BaseParam();
        setCommonParam(param, request);
        List<TnmSystemMasterEntity> entities = getMasterByMasterIdStr(masterIdStr, param);
        List<TnmSystemMasterEntity> readOnly = new ArrayList<>();
        List<TnmSystemMasterEntity> notReadOnly = new ArrayList<>();

        for (TnmSystemMasterEntity entity : entities) {
            if (entity.getReadOnly() == CodeConst.SystemMaster.READONLY) {
                readOnly.add(entity);
            } else {
                notReadOnly.add(entity);
            }
        }
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String zipName = PREFIX + format.format(new Date()) + ".zip";
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setCharacterEncoding(ENCODE);
        response.setHeader("Content-disposition",
                StringUtil.formatMessage("attachment; filename=\"{0}\"", encodeClientFileName(zipName, request)));
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(response.getOutputStream());
            if (!readOnly.isEmpty()) {
                param = new BaseParam();
                param.setSwapData("data", readOnly);
                this.makeZipExcelWithTemplate(PREFIX_VIEW_ONLY + format.format(new Date()) + ".xlsx", param, zipOutputStream);
            }
            if (!notReadOnly.isEmpty()) {
                param = new BaseParam();
                param.setSwapData("data", notReadOnly);
                this.makeZipExcelWithTemplate(PREFIX_NOT_VIEW_ONLY + format.format(new Date()) + ".xlsx", param, zipOutputStream);
            }
        } catch (Exception ex) {
            log.error("", ex);
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public BaseResponse upload(@RequestParam(value = "file") MultipartFile file,
                               @RequestParam(value = "masterIdStr", required = false, defaultValue = "") String masterIdStr, HttpServletRequest request) throws IOException {
        String fileName = file.getOriginalFilename();
        if (StringUtil.isEmpty(fileName) && file.getSize() == 0) {
            throw new BusinessException("W0007");
        }
        checkUploadFileName(fileName);
        BaseParam param = new BaseParam();
        setCommonParam(param, request);
        List<TnmSystemMasterEntity> entities = getMasterByMasterIdStr(masterIdStr, param);
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            for (TnmSystemMasterEntity entity : entities) {
                Sheet sheet = workbook.getSheet(entity.getMasterName());
                if (sheet == null) {
                    throw new BusinessException("W0008");
                }
            }
            for (TnmSystemMasterEntity entity : entities) {
                processUploadSheet(entity.getMasterName(), workbook.getSheet(entity.getMasterName()));
            }
        } catch (InvalidFormatException e) {
            throw new BusinessException("W0008");
        }
        return new BaseResponse(BaseResponse.STATUS_SUCCESS);
    }


    private void checkUploadFileName(String fileName) {
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (name.length() != PREFIX.length() + DATE_FORMAT.length()
                || (!ext.equalsIgnoreCase("xls") && !ext.equalsIgnoreCase("xlsx"))) {
            throw new BusinessException("W0008");
        }
        String date = name.substring(PREFIX.length());
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            format.parse(date);
        } catch (ParseException e) {
            log.error(String.format("Bad upload file name %s", fileName));
            throw new BusinessException("W0008");
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    protected <T extends BaseParam> void writeContentToExcel(T param, Workbook wbTemplate, SXSSFWorkbook wbOutput) {
        List<TnmSystemMasterEntity> list = (List<TnmSystemMasterEntity>) param.getSwapData().get("data");
        Map<String, List<HashMap<String, Object>>> sheets = new HashMap<>();
        for (TnmSystemMasterEntity entity : list) {
            String masterName = entity.getMasterName();
            sheets.put(masterName, systemMasterService.loadMasterSpec(masterName));
        }
        if (!sheets.isEmpty()) {
            Iterator<Map.Entry<String, List<HashMap<String, Object>>>> iterator = sheets.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<HashMap<String, Object>>> entry = iterator.next();
                String sheetName = entry.getKey();
                List<HashMap<String, Object>> content = entry.getValue();
                Sheet sheet = wbOutput.getSheet(sheetName);
                processDownloadSheet(sheetName, sheet, content);
            }
        }
        deleteUnusedSheets(wbOutput, sheets.keySet());
    }

    //save sheet data into database
    private void processUploadSheet(String masterName, Sheet sheet) {
    }

    //put data into sheet
    private void processDownloadSheet(String masterName, Sheet sheet, List<HashMap<String, Object>> content) {

    }

    private void deleteUnusedSheets(SXSSFWorkbook wbOutput, Set<String> usedSheetName) {
        int size = wbOutput.getNumberOfSheets();
        Set<String> unused = new HashSet<>();
        for (int i = 0; i < size; i++) {
            Sheet sheet = wbOutput.getSheetAt(i);
            if (!usedSheetName.contains(sheet.getSheetName())) {
                unused.add(sheet.getSheetName());
            }
        }
        for (String name : unused) {
            wbOutput.removeSheetAt(wbOutput.getSheetIndex(name));
        }
    }


    @Override
    protected String getFileId() {
        return "QSM0101";
    }

    private List<TnmSystemMasterEntity> getMasterByMasterIdStr(String masterIdStr, BaseParam param) {
        String[] idArray = masterIdStr.split(",");
        if (idArray.length == 0) throw new BusinessException("W0005");
        List<Integer> array = new ArrayList<>();
        for (String id : idArray) {
            array.add(Integer.parseInt(id));
        }
        return systemMasterService.listAll(param, array);
    }
}
