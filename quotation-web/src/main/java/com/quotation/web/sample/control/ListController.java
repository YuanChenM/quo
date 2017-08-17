package com.quotation.web.sample.control;

import com.quotation.core.base.BaseController;
import com.quotation.core.bean.BaseParam;
import com.quotation.core.consts.NumberConst;
import com.quotation.core.exception.BusinessException;
import com.quotation.core.util.StringUtil;
import com.quotation.web.sample.entity.SampleEntity;
import com.quotation.web.sample.service.ListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yuan_chen1 on 2017/7/19.
 * <p>
 * ListController
 */
@Controller
public class ListController extends BaseController {

    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ListController.class);

    @Autowired
    private ListService listService;

    @RequestMapping(value = "/sp/list")
    public String init(Model model, HttpServletRequest request) {

        BaseParam param = new BaseParam();
        List<SampleEntity> samples = listService.getSamples(param);
        model.addAttribute("samples", samples);
        return "sample/List";
    }

    private void loadOne(String sampleUid, Model model) {
        logger.debug("load one begin!");

        SampleEntity entity = new SampleEntity();
        if (!StringUtil.isEmpty(sampleUid) && !StringUtil.equals(sampleUid, "new")) {
            BaseParam param = new BaseParam();
            param.getFilters().put("sampleUid", sampleUid);
            entity = listService.getSample(param);
        } else {
            entity.setSampleId(listService.getMaxId());
        }

        model.addAttribute("entity", entity);
        logger.debug("load one over!");
    }

    /**
     * edit
     *
     * @param sampleUid the form bean
     * @param model     the form bean
     * @param request   the request information
     * @return "/sample/edit/{sampleUid}" the return page
     */
    @RequestMapping(value = "/sp/edit/{sampleUid}")
    public String edit(@PathVariable String sampleUid, Model model, HttpServletRequest request) {
        this.loadOne(sampleUid, model);
        return "sample/Edit";
    }

    /**
     * getOne
     *
     * @param sampleUid the form bean
     * @param model     the form bean
     * @param request   the request information
     * @return "/sample/popup/{sampleUid}" the return page
     */
    @RequestMapping(value = "/sp/popup/{sampleUid}")
    public String getOne(@PathVariable String sampleUid, Model model, HttpServletRequest request) {
        this.loadOne(sampleUid, model);
        return "sample/Popup";
    }

    /**
     * editSave
     *
     * @param model   the form bean
     * @param entity  the form bean
     * @param request the request information
     * @return "/sample/edit/save" the return page
     */
    @RequestMapping(value = "/sp/edit/save", method = RequestMethod.POST)
    public String editSave(Model model, @ModelAttribute("entity") SampleEntity entity, HttpServletRequest request) {
        this.saveSample(model, entity);
        return "forward:/sp/list";
    }

    /**
     * popupSave
     *
     * @param model   the form bean
     * @param entity  the form bean
     * @param request the request information
     * @return "/sample/popup/save the return page
     */
    @RequestMapping(value = "/sp/popup/save")
    public String popupSave(Model model, @ModelAttribute("entity") SampleEntity entity, HttpServletRequest request) {
        this.saveSample(model, entity);
        return "sample/Popup";
    }

    private void saveSample(Model model, SampleEntity entity) {
        if (!StringUtil.isEmpty(entity.getSampleCode())) {
            boolean isExistData;
            if (StringUtil.isEmpty(entity.getSampleUid())) {
                isExistData = false;
            } else {
                BaseParam param = new BaseParam();
                param.getFilters().put("sampleUid", entity.getSampleUid());
                isExistData = listService.existData(param);
            }

            int result;
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (isExistData) {
                entity.setUpdatedDate(now);
                entity.setUpdatedBy(NumberConst.IntDef.INT_ZERO);
                result = listService.modifySample(entity);
                if (result != 1) {
                    throw new BusinessException("update is fail.");
                }
            } else {
                entity.setCreatedBy(NumberConst.IntDef.INT_ZERO);
                entity.setCreatedDate(now);
                entity.setUpdatedBy(NumberConst.IntDef.INT_ZERO);
                entity.setUpdatedDate(now);
                entity.setVersion(NumberConst.IntDef.INT_ONE);
                result = listService.addSample(entity);
                if (result != 1) {
                    throw new BusinessException("add is fail.");
                }
            }
        }
        model.addAttribute("isSuccess", true);
    }

    @RequestMapping(value = "/sp/delete/{sampleUid}")
    public String delete(@PathVariable String sampleUid, Model model, HttpServletRequest request) {
        SampleEntity param = new SampleEntity();
        param.setSampleUid(sampleUid);
        int result = listService.removeSample(param);
        if (result != 1) {
            throw new BusinessException("delete is fail.");
        }
        return init(model, request);
    }
}
