/**
 * @screen Stock Status file manager
 * 
 */
package com.quotation.common.util;

import com.quotation.common.bean.TntRdDetailAttachEx;
import com.quotation.common.bean.TntStockStatusEx;
import com.quotation.common.bean.TntStockStatusHeader;
import com.quotation.common.consts.CodeConst.*;
import com.quotation.common.consts.QuotationConst.Language;
import com.quotation.common.entity.TntRdAttachShipping;
import com.quotation.core.consts.NumberConst.IntDef;
import com.quotation.core.consts.StringConst;
import com.quotation.core.util.DateTimeUtil;
import com.quotation.core.util.DecimalUtil;
import com.quotation.core.util.PoiUtil;
import com.quotation.core.util.StringUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * Stock status file manager.
 * 
 * 
 */
public class StockStatusManager {

    /** sheet name */
    private final static String SHEET_NAME_STYLE = "styleSheet";

    /** sheet name */
    private final static String SHEET_NAME_WORKING = "Stock Status";

    /** template DATE_WHITE_RED_FONT */
    private final static String DATE_WHITE_RED_FONT = "DATE_WHITE_RED_FONT";

    /** template DATE_WHITE_NORMAL */
    private final static String DATE_WHITE_NORMAL = "DATE_WHITE_NORMAL";

    /** template MONTH_PREFIX */
    private final static String MONTH_PREFIX = "MONTH_PREFIX";

    /** template DATE_GREEN_NORMAL */
    private final static String DATE_GREEN_NORMAL = "DATE_GREEN_NORMAL";

    /** template DATE_GREEN_RED_FONT */
    private final static String DATE_GREEN_RED_FONT = "DATE_GREEN_RED_FONT";

    /** template LABEL_WHITE_LEFT */
    private final static String LABEL_WHITE_LEFT = "LABEL_WHITE_LEFT";

    /** template LABEL_WHITE_CENTER */
    private final static String LABEL_WHITE_CENTER = "LABEL_WHITE_CENTER";

    /** template LABEL_WHITE_RED_CENTER */
    private final static String LABEL_WHITE_RED_CENTER = "LABEL_WHITE_RED_CENTER";

    /** template LABEL_GREEN_LEFT */
    private final static String LABEL_GREEN_LEFT = "LABEL_GREEN_LEFT";

    /** template LABEL_GREEN_CENTER */
    private final static String LABEL_GREEN_CENTER = "LABEL_GREEN_CENTER";

    /** template LABEL_GREEN_RED_CENTER */
    private final static String LABEL_GREEN_RED_CENTER = "LABEL_GREEN_RED_CENTER";

    /** template label gray 15% */
    private final static String LABEL_GRAY15 = "LABEL_GRAY15";

    /** template decimal percentage */
    private final static String DECIMAL_PERCENTAGE = "DECIMAL_PERCENTAGE";

    /** template decimal percentage */
    private final static String DECIMAL_PERCENTAGE_GREEN = "DECIMAL_PERCENTAGE_GREEN";

    /** template decimal percentage */
    private final static String NORMAL_LABEL_WO_LINE = "NORMAL_LABEL_WO_LINE";

    /** template decimal white */
    private final static String DECIMAL_WHITE = "DECIMAL_WHITE_";

    /** template decimal green */
    private final static String DECIMAL_GREEN = "DECIMAL_GREEN_";

    /** template decimal white */
    private final static String DECIMAL_WHITE_RED_FONT = "DECIMAL_WHITE_RED_FONT_";

    /** template decimal green */
    private final static String DECIMAL_GREEN_RED_FONT = "DECIMAL_GREEN_RED_FONT_";

    /** point width of column */
    private final static double POINT_WIDTH = 357;

    /** CPSSSF01_Label_NoCustomerStock=No Customer Stock */
    private final static String LABEL_NOCUSTOMERSTOCK = "CPSSSF01_Label_NoCustomerStock";

    /** CPSSSF01_Label_NoOrderForecast=No Order Forecast */
    private final static String LABEL_NOORDERFORECAST = "CPSSSF01_Label_NoOrderForecast";

    /** CPSSSF01_Label_NoCustomerForecast=No Customer Forecast */
    private final static String LABEL_NOCUSTOMERFORECAST = "CPSSSF01_Label_NoCustomerForecast";

    /** CPSSSF01_Label_NoOrderCustomerForecast */
    private final static String LABEL_NOORDERCUSTOMERFORECAST = "CPSSSF01_Label_NoOrderCustomerForecast";

    /** CPSSSF01_Label_NoOrderCustomerForecast */
    private final static String DOWNLOAD_TIME = "CPSSSF01_Label_DownloadTime";

    /** CPSSSF01_Label_NoOrderCustomerForecast */
    private final static String SYNC_SSMS_DATE_TIME = "CPSSSF01_Label_LastSSMSInterfaceTime";

    /** CPSSSF01_Label_NoOrderCustomerForecast */
    private final static String SYNC_VV_DATE_TIME = "CPSRDF01_Label_LastVVInterfaceTime";

    /** CPSSSF01_Label_NoOrderCustomerForecast */
    private final static String SYNC_AISIN_DATE_TIME = "CPSRDF01_Label_LastASInterfaceTime";

    /** CPSSSF01_Label_NoOrderCustomerForecast */
    private final static String FORECAST_LABEL = "CPSSSF01_Grid_Forecast";

    /**
     * Prepare style map for stock status report run-down file.
     *
     * @param wbTemplate wbTemplate
     * @param styleMap style map
     * @param lang language
     */
    public static void prepStyleMapStockStatus(Workbook wbTemplate, Map<String, CellStyle> styleMap, Language lang) {

        // get style Sheet
        Sheet styleSheet = wbTemplate.getSheet(SHEET_NAME_STYLE);

        // prepare label
        // styleMap.put(LABEL_GRAY25, PoiUtil.getCell(styleSheet, IntDef.INT_TWO, IntDef.INT_ONE).getCellStyle());
        styleMap.put(LABEL_WHITE_LEFT, PoiUtil.getCell(styleSheet, IntDef.INT_THREE, IntDef.INT_ONE).getCellStyle());
        styleMap.put(LABEL_WHITE_CENTER, PoiUtil.getCell(styleSheet, IntDef.INT_FOUR, IntDef.INT_ONE).getCellStyle());
        styleMap.put(LABEL_WHITE_RED_CENTER, PoiUtil.getCell(styleSheet, IntDef.INT_FIVE, IntDef.INT_ONE)
            .getCellStyle());
        styleMap.put(LABEL_GREEN_LEFT, PoiUtil.getCell(styleSheet, IntDef.INT_SIX, IntDef.INT_ONE).getCellStyle());
        styleMap.put(LABEL_GREEN_CENTER, PoiUtil.getCell(styleSheet, IntDef.INT_SEVEN, IntDef.INT_ONE).getCellStyle());
        styleMap.put(LABEL_GREEN_RED_CENTER, PoiUtil.getCell(styleSheet, IntDef.INT_EIGHT, IntDef.INT_ONE)
            .getCellStyle());
        styleMap.put(LABEL_GRAY15, PoiUtil.getCell(styleSheet, IntDef.INT_NINE, IntDef.INT_ONE).getCellStyle());
        styleMap.put(NORMAL_LABEL_WO_LINE, PoiUtil.getCell(styleSheet, IntDef.INT_TWELVE, IntDef.INT_ONE)
            .getCellStyle());

        // prepare Date
        // check language
        if (Language.CHINESE.equals(lang)) {
            styleMap.put(DATE_WHITE_RED_FONT, PoiUtil.getCell(styleSheet, IntDef.INT_TWO, IntDef.INT_TWO)
                .getCellStyle());
            styleMap
                .put(DATE_WHITE_NORMAL, PoiUtil.getCell(styleSheet, IntDef.INT_FOUR, IntDef.INT_TWO).getCellStyle());
            styleMap.put(MONTH_PREFIX, PoiUtil.getCell(styleSheet, IntDef.INT_SIX, IntDef.INT_TWO).getCellStyle());
            styleMap.put(DATE_GREEN_NORMAL, PoiUtil.getCell(styleSheet, IntDef.INT_EIGHT, IntDef.INT_TWO)
                .getCellStyle());
            styleMap.put(DATE_GREEN_RED_FONT, PoiUtil.getCell(styleSheet, IntDef.INT_TEN, IntDef.INT_TWO)
                .getCellStyle());
        } else {
            styleMap.put(DATE_WHITE_RED_FONT, PoiUtil.getCell(styleSheet, IntDef.INT_THREE, IntDef.INT_TWO)
                .getCellStyle());
            styleMap
                .put(DATE_WHITE_NORMAL, PoiUtil.getCell(styleSheet, IntDef.INT_FIVE, IntDef.INT_TWO).getCellStyle());
            styleMap.put(MONTH_PREFIX, PoiUtil.getCell(styleSheet, IntDef.INT_SEVEN, IntDef.INT_TWO).getCellStyle());
            styleMap
                .put(DATE_GREEN_NORMAL, PoiUtil.getCell(styleSheet, IntDef.INT_NINE, IntDef.INT_TWO).getCellStyle());
            styleMap.put(DATE_GREEN_RED_FONT, PoiUtil.getCell(styleSheet, IntDef.INT_ELEVEN, IntDef.INT_TWO)
                .getCellStyle());
        }

        // prepare Decimal Percentage
        styleMap.put(DECIMAL_PERCENTAGE, PoiUtil.getCell(styleSheet, IntDef.INT_TEN, IntDef.INT_ONE).getCellStyle());
        styleMap.put(DECIMAL_PERCENTAGE_GREEN, PoiUtil.getCell(styleSheet, IntDef.INT_ELEVEN, IntDef.INT_ONE)
            .getCellStyle());

        // prepare Decimal
        StringBuffer sb = new StringBuffer();
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_SEVEN; i++) {

            // get current row
            int row = IntDef.INT_TWO + i;

            // write
            sb.setLength(IntDef.INT_ZERO);
            sb.append(DECIMAL_WHITE).append(i);
            styleMap.put(sb.toString(), PoiUtil.getCell(styleSheet, row, IntDef.INT_THREE).getCellStyle());
            // green
            sb.setLength(IntDef.INT_ZERO);
            sb.append(DECIMAL_GREEN).append(i);
            styleMap.put(sb.toString(), PoiUtil.getCell(styleSheet, row, IntDef.INT_FOUR).getCellStyle());
            // write
            sb.setLength(IntDef.INT_ZERO);
            sb.append(DECIMAL_WHITE_RED_FONT).append(i);
            styleMap.put(sb.toString(), PoiUtil.getCell(styleSheet, row, IntDef.INT_FIVE).getCellStyle());
            // green
            sb.setLength(IntDef.INT_ZERO);
            sb.append(DECIMAL_GREEN_RED_FONT).append(i);
            styleMap.put(sb.toString(), PoiUtil.getCell(styleSheet, row, IntDef.INT_SIX).getCellStyle());
        }
    }

    /**
     * prepare header content of Stock status report.
     *
     * @param workBook current work book
     * @param lang language
     * @param headMap head information Map
     * @param header stock status header
     */
    public static void setHeaderForStockStatus(Workbook workBook, Language lang, Map<Integer, String> headMap,
        TntStockStatusHeader header) {

        // prepare header
        int cntRow = IntDef.INT_FOUR;
        int styRow = IntDef.INT_FOURTEEN;
        int cntCol = IntDef.INT_THIRTY;
        int styCol = IntDef.INT_ONE;

        // get style sheet
        Sheet stySheet = workBook.getSheet(SHEET_NAME_STYLE);
        // get working sheet
        Sheet cntSheet = workBook.getSheet(SHEET_NAME_WORKING);

        // prepare on-shipping information
        // set Total On-Shipping Qty
        copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
        mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
        cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));
        // set on-shipping detail
        cntCol++;
        styCol = lang.equals(Language.CHINESE) ? styCol + IntDef.INT_TWO : styCol + IntDef.INT_ONE;
        List<TntRdDetailAttachEx> onShippingList = header.getOnShippingDetailList();
        int size = onShippingList.size();
        for (int i = IntDef.INT_ZERO; i < size; i++) {

            // get shipping detail information
            TntRdDetailAttachEx detail = onShippingList.get(i);

            // copy cells
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_EIGHTEEN));

            // set etd
            PoiUtil.setCellValue(cntSheet, cntRow + IntDef.INT_TWO, cntCol, detail.getEtd());
            // set eta
            PoiUtil.setCellValue(cntSheet, cntRow + IntDef.INT_FOUR, cntCol, detail.getEta());
            // set import in-bound date
            PoiUtil.setCellValue(cntSheet, cntRow + IntDef.INT_SIX, cntCol, detail.getImpInbPlanDate());

            // string key
            StringBuffer key = new StringBuffer();
            key.append(DateTimeUtil.formatDate(DateTimeUtil.FORMAT_YYYYMMDD, detail.getEtd()));
            key.append(StringConst.MIDDLE_LINE);
            key.append(DateTimeUtil.formatDate(DateTimeUtil.FORMAT_YYYYMMDD, detail.getEta()));
            key.append(StringConst.MIDDLE_LINE);
            key.append(DateTimeUtil.formatDate(DateTimeUtil.FORMAT_YYYYMMDD, detail.getImpInbPlanDate()));

            // set into header map
            headMap.put(cntCol, key.toString());

            // next
            cntCol++;
        }
        // set OnShipping title(Merge)
        if (size > IntDef.INT_ZERO) {

            // merge
            mergeCells(cntSheet, cntRow, cntRow, cntCol - IntDef.INT_ONE - size, cntCol - IntDef.INT_ONE);
            // group
            cntSheet.groupColumn(cntCol - IntDef.INT_ONE - size, cntCol - IntDef.INT_TWO);
            cntSheet.setColumnGroupCollapsed(cntCol - IntDef.INT_TWO, true);
        }

        // Imp Customs Clearnance
        styCol = lang.equals(Language.CHINESE) ? styCol + IntDef.INT_TWO : styCol + IntDef.INT_THREE;
        copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
        mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
        cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));

        // Imp W/H Stock
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_FOUR; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));
        }
        // merge
        mergeCells(cntSheet, cntRow, cntRow, cntCol - IntDef.INT_THREE, cntCol);

        // Customer Stock
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_TWO; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_TWENTY_TWO));
        }
        // merge
        mergeCells(cntSheet, cntRow, cntRow, cntCol - IntDef.INT_ONE, cntCol);
        // group
        cntSheet.groupColumn(cntCol - IntDef.INT_ONE, cntCol - IntDef.INT_ONE);
        cntSheet.setColumnGroupCollapsed(cntCol - IntDef.INT_ONE, true);

        // Total Supply Chain Stock Qty
        cntCol++;
        styCol++;
        copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
        mergeCells(cntSheet, cntRow, cntRow + IntDef.INT_SIX, cntCol, cntCol);
        cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));

        // Not In Rundown
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_FOUR; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));
        }
        // merge
        mergeCells(cntSheet, cntRow, cntRow, cntCol - IntDef.INT_THREE, cntCol);
        // group
        cntSheet.groupColumn(cntCol - IntDef.INT_THREE, cntCol - IntDef.INT_ONE);
        cntSheet.setColumnGroupCollapsed(cntCol - IntDef.INT_ONE, true);

        // alarm 1
        for (int m = IntDef.INT_ZERO; m < IntDef.INT_TWO; m++) {

            // content
            for (int i = IntDef.INT_ZERO; i < IntDef.INT_FIVE; i++) {
                cntCol++;
                styCol++;
                copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
                mergeCells(cntSheet, cntRow + IntDef.INT_TWO, cntRow + IntDef.INT_SIX, cntCol, cntCol);
                cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_EIGHTEEN));
            }
            // next month
            for (int i = IntDef.INT_ZERO; i < IntDef.INT_FOUR; i++) {
                cntCol++;
                styCol++;
                copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
                mergeCells(cntSheet, cntRow + IntDef.INT_THREE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
                cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_EIGHTEEN));
            }
            // merge
            mergeCells(cntSheet, cntRow + IntDef.INT_TWO, cntRow + IntDef.INT_TWO, cntCol - IntDef.INT_THREE, cntCol);
            // merge
            mergeCells(cntSheet, cntRow, cntRow + IntDef.INT_ONE, cntCol - IntDef.INT_EIGHT, cntCol);
        }

        // alarm 2
        // target Month
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_TEN; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_TWO, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_EIGHTEEN));
        }
        // merge
        mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_ONE, cntCol - IntDef.INT_NINE, cntCol);

        // forecast month
        // set into map
        headMap.put(cntCol + IntDef.INT_ONE, StringUtil.toString(header.getMaxFCNum()));
        // set into header map
        if (header.getMaxFCNum() != null) {
            // loop set title
            for (int f = IntDef.INT_ONE; f <= header.getMaxFCNum(); f++) {
                // last title
                for (int i = IntDef.INT_ZERO; i < IntDef.INT_EIGHT; i++) {
                    cntCol++;
                    styCol++;
                    copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
                    mergeCells(cntSheet, cntRow + IntDef.INT_TWO, cntRow + IntDef.INT_SIX, cntCol, cntCol);
                    cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_EIGHTEEN));
                }

                // rename forecast name
                StringBuffer fcTitle = new StringBuffer(MessageManager.getMessage(FORECAST_LABEL, lang.getLocale()));
                fcTitle.append(StringConst.BLANK).append(f);
                // merge
                mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_ONE, cntCol - IntDef.INT_SEVEN,
                    cntCol);
                // reset
                PoiUtil.setCellValue(cntSheet, cntRow + IntDef.INT_ONE, cntCol - IntDef.INT_SEVEN, fcTitle.toString());

                // group
                cntSheet.groupColumn(cntCol - IntDef.INT_SIX, cntCol - IntDef.INT_ONE);
                cntSheet.setColumnGroupCollapsed(cntCol - IntDef.INT_ONE, true);

                // go to first column
                styCol -= IntDef.INT_EIGHT;
            }
        }

        // get fc number
        int fcNum = header.getMaxFCNum() == null ? IntDef.INT_ZERO : header.getMaxFCNum().intValue();
        // merge all
        mergeCells(cntSheet, cntRow, cntRow, cntCol - IntDef.INT_NINE - fcNum * IntDef.INT_EIGHT, cntCol);

        // prepare style column
        styCol += IntDef.INT_EIGHT;

        // alarm 3
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_SEVEN; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_TWO, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_EIGHTEEN));
        }
        // merge
        mergeCells(cntSheet, cntRow, cntRow + IntDef.INT_ONE, cntCol - IntDef.INT_SIX, cntCol);

        // alarm 4
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_THREE; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_TWO, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_EIGHTEEN));
        }
        // merge
        mergeCells(cntSheet, cntRow, cntRow + IntDef.INT_ONE, cntCol - IntDef.INT_TWO, cntCol);

        // Alarm Master Data
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_FIVE; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));
        }
        // merge
        mergeCells(cntSheet, cntRow, cntRow, cntCol - IntDef.INT_FOUR, cntCol);
        // group
        cntSheet.groupColumn(cntCol - IntDef.INT_FOUR, cntCol - IntDef.INT_ONE);
        cntSheet.setColumnGroupCollapsed(cntCol - IntDef.INT_ONE, true);

        // Imp Outbound Plan Input
        cntCol++;
        styCol++;
        copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
        mergeCells(cntSheet, cntRow, cntRow + IntDef.INT_SIX, cntCol, cntCol);
        cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));

        // Build out Data
        for (int i = IntDef.INT_ZERO; i < IntDef.INT_FOUR; i++) {
            cntCol++;
            styCol++;
            copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
            mergeCells(cntSheet, cntRow + IntDef.INT_ONE, cntRow + IntDef.INT_SIX, cntCol, cntCol);
            cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));
        }
        // merge
        mergeCells(cntSheet, cntRow, cntRow, cntCol - IntDef.INT_THREE, cntCol);
        // group
        cntSheet.groupColumn(cntCol - IntDef.INT_THREE, cntCol - IntDef.INT_ONE);
        cntSheet.setColumnGroupCollapsed(cntCol - IntDef.INT_ONE, true);

        // Imp Outbound Plan Input
        cntCol++;
        styCol++;
        copyCells(stySheet, cntSheet, styRow, styCol, cntRow, cntCol, IntDef.INT_SEVEN);
        mergeCells(cntSheet, cntRow, cntRow + IntDef.INT_SIX, cntCol, cntCol);
        cntSheet.setColumnWidth(cntCol - IntDef.INT_ONE, (short) (POINT_WIDTH * IntDef.INT_FIFTEEN));
    }

    /**
     * Copy cell with column.
     *
     * @param fromSheet fromSheet
     * @param toSheet toSheet
     * @param fmRow from Row
     * @param fmCol from column
     * @param toRow to row
     * @param toCol to column
     * @param rows rows
     */
    private static void copyCells(Sheet fromSheet, Sheet toSheet, int fmRow, int fmCol, int toRow, int toCol, int rows) {

        // define
        Cell srcCell = null;
        Cell targCell = null;

        // loop set
        for (int i = IntDef.INT_ZERO; i < rows; i++) {

            // from cell
            srcCell = PoiUtil.getCell(fromSheet, fmRow + i, fmCol);
            // target cell
            targCell = PoiUtil.getOrCreateCell(toSheet, toRow + i, toCol);
            // copy
            copyCell(srcCell, targCell, true);
        }
    }

    /**
     * Copy cell.
     * 
     * @param srcCell source cell
     * @param targCell target cell
     * @param isCopyValue need do copy values
     */
    private static void copyCell(Cell srcCell, Cell targCell, boolean isCopyValue) {

        // copy
        targCell.setCellStyle(srcCell.getCellStyle());
        targCell.setCellType(srcCell.getCellType());

        // copy values
        if (isCopyValue) {
            targCell.setCellValue(srcCell.getRichStringCellValue());
        }
    }

    /**
     * Merge cells.
     *
     * @param sheet sheet
     * @param startRow start row
     * @param endRow end row
     * @param startCol start column
     * @param endCol end column
     */
    private static void mergeCells(Sheet sheet, int startRow, int endRow, int startCol, int endCol) {

        // get cell range address
        CellRangeAddress add = new CellRangeAddress(startRow - IntDef.INT_ONE, endRow - IntDef.INT_ONE, startCol
                - IntDef.INT_ONE, endCol - IntDef.INT_ONE);

        // add
        sheet.addMergedRegion(add);
    }

    /**
     * Set stock status detail information by each parts.
     *
     * @param workbook work book
     * @param stockStatusList stock status list
     * @param lang language
     * @param styleMap style map
     * @param headMap head map
     * @param office office code
     * @param dateList sync date time list
     */
    public static void setStockStatusDetailInfo(SXSSFWorkbook workbook, List<TntStockStatusEx> stockStatusList,
        Language lang, Map<String, CellStyle> styleMap, Map<Integer, String> headMap, String office, Date[] dateList) {

        // get sheet
        Sheet cntSheet = workbook.getSheet(SHEET_NAME_WORKING);
        int row = IntDef.INT_ELEVEN;
        int col = IntDef.INT_ONE;
        // get labels
        // get no customer stock update
        String noCustomerStock = MessageManager.getMessage(LABEL_NOCUSTOMERSTOCK, lang.getName());
        // get no customer forecast
        String noCustForecast = MessageManager.getMessage(LABEL_NOCUSTOMERFORECAST, lang.getName());
        // get header map key
        // get head map key set
        Set<Integer> colSet = headMap.keySet();
        List<Integer> colList = new ArrayList<Integer>(colSet);
        Collections.sort(colList);
        // get max forecast number
        Integer maxFcNum = StringUtil.toInteger(headMap.get(colList.get(colList.size() - IntDef.INT_ONE)));
        maxFcNum = maxFcNum == null ? IntDef.INT_ZERO : maxFcNum;
        // style key
        StringBuffer styleKeyBuffer = new StringBuffer();
        CellStyle decimalStyle = null;
        StringBuffer formula = new StringBuffer();
        StringBuffer supplyFormula = new StringBuffer();
        String styleKey = null;
        String alarmFlag = null;
        // get no master style
        CellStyle noMasterStyle = null;
        CellStyle alarmStyle = null;
        // get no master style
        CellStyle labelGrayStyle = styleMap.get(LABEL_GRAY15);
        CellStyle normalLabelStyle = styleMap.get(LABEL_WHITE_LEFT);

        // loop each stock status information from information list
        for (TntStockStatusEx stockStatus : stockStatusList) {
            // set no master style
            noMasterStyle = styleMap.get(stockStatus.isHasMoreSupplier() ? LABEL_GREEN_RED_CENTER
                : LABEL_WHITE_RED_CENTER);
            // TTC Parts No.
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getTtcPartsNo());
            // Parts Name In English
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getPartsNameEn());
            // Parts Name In Chinese
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getPartsNameCn());
            // Exp Country
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_CENTER));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getExpCounty());
            // TTC Supplier Code
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getSupplierCode());
            // Supplier Parts No.
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getSupplierPartsNo());
            // Imp Country
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_CENTER));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getImpCountry());
            // Imp Office Code
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getImpOfficeCode());
            // TTC Customer Code
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getCustomerCode());
            // Old Part No.
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getOldPartsNo());
            // Customer Part No.
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getCustPartsNo());
            // Customer Back No.
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getCustBackNo());
            // Business Type
            String businessType = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.BUSINESS_TYPE,
                stockStatus.getBusinessType());
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, businessType);
            // Parts Type
            String partsType = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.PARTS_TYPE,
                stockStatus.getPartsType());
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, partsType);
            // SPQ
            styleKeyBuffer.setLength(IntDef.INT_ZERO);
            styleKeyBuffer.append(DECIMAL_WHITE).append(stockStatus.getDigit());
            decimalStyle = styleMap.get(styleKeyBuffer.toString());
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getSpq());
            // Car Model
            PoiUtil.setCellStyle(cntSheet, row, col, normalLabelStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getCarModel());
            // On Shipping Delay or Advanced Adjustment Pattern
            String adjPattern = CodeCategoryManager.getCodeName(lang.getCode(),
                CodeMasterCategory.ON_SHIPPING_DELAY_ADJUST_P, stockStatus.getDelayAdjustmentPattern());
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_CENTER));
            PoiUtil.setCellValue(cntSheet, row, col++, adjPattern);
            // Exp Balance Order
            // Total Exp Balance Order
            if (stockStatus.getBusinessPattern().equals(BusinessPattern.V_V)) {
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                formula.setLength(IntDef.INT_ZERO);
                formula.append(CellReference.convertNumToColString(col));
                formula.append(row).append(StringConst.PLUS);
                formula.append(CellReference.convertNumToColString(col + IntDef.INT_FIVE));
                formula.append(row);
                PoiUtil.setCellFormula(cntSheet, row, col++, formula.toString());
            } else {
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getExpFuturePlanQty());
            }
            // supplyFormula
            supplyFormula.setLength(IntDef.INT_ZERO);
            supplyFormula.append("SUM(");
            supplyFormula.append(CellReference.convertNumToColString(col - IntDef.INT_TWO));
            supplyFormula.append(row).append(StringConst.COMMA);
            // Total Outstanding Delivery Qty
            if (stockStatus.getBusinessPattern().equals(BusinessPattern.V_V)) {
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getExpPlanDelayQty());
            } else {
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            // Outstanding Delivery Qty
            // Monthly (SEA)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Monthly (AIR)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Additional (SEA)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Additional (AIR)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Total Future Delivery Qty
            if (stockStatus.getBusinessPattern().equals(BusinessPattern.V_V)) {
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getExpFuturePlanQty());
            } else {
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            // Future Delivery Qty
            // Monthly (SEA)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Monthly (AIR)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Additional (SEA)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Additional (AIR)
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            // Exp W/H Stock
            // Total Stock Qty
            if (stockStatus.getBusinessPattern().equals(BusinessPattern.V_V)) {
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getExpStockQty());
            } else {
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            supplyFormula.append(CellReference.convertNumToColString(col - IntDef.INT_TWO));
            supplyFormula.append(row).append(StringConst.COMMA);
            // On Shipping
            // Total On Shipping Qty
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            formula.setLength(IntDef.INT_ZERO);
            if (headMap.size() > IntDef.INT_ONE) {
                formula.append("SUM(");
                formula.append(CellReference.convertNumToColString(col));
                formula.append(row).append(StringConst.COLON);
                formula.append(CellReference.convertNumToColString(col + headMap.size() - IntDef.INT_TWO));
                formula.append(row).append(StringConst.RIGHT_BRACKET);
                // set
                PoiUtil.setCellFormula(cntSheet, row, col, formula.toString());
            } else {
                PoiUtil.setCellValue(cntSheet, row, col, BigDecimal.ZERO);
            }
            supplyFormula.append(CellReference.convertNumToColString(col - IntDef.INT_ONE));
            supplyFormula.append(row).append(StringConst.COMMA);
            // On Shipping detail
            int idx = IntDef.INT_ZERO;
            List<TntRdAttachShipping> onShppingList = stockStatus.getOnShippingList();
            int loopSize = colList.size() - IntDef.INT_ONE;
            StringBuffer shipKey = new StringBuffer();
            for (int s = IntDef.INT_ZERO; s < loopSize; s++) {
                // get column index
                col = colList.get(s);
                // get values
                String shipInfo = headMap.get(col);
                // set style
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                // get on-shipping information
                for (; idx < onShppingList.size(); idx++) {
                    // get detail
                    TntRdAttachShipping detail = onShppingList.get(idx);
                    // prepare key
                    shipKey.setLength(IntDef.INT_ZERO);
                    shipKey.append(DateTimeUtil.formatDate(DateTimeUtil.FORMAT_YYYYMMDD, detail.getEtd()));
                    shipKey.append(StringConst.MIDDLE_LINE);
                    shipKey.append(DateTimeUtil.formatDate(DateTimeUtil.FORMAT_YYYYMMDD, detail.getEta()));
                    shipKey.append(StringConst.MIDDLE_LINE);
                    shipKey.append(DateTimeUtil.formatDate(DateTimeUtil.FORMAT_YYYYMMDD, detail.getImpInbPlanDate()));
                    // compare key
                    int comp = shipInfo.compareTo(shipKey.toString());
                    if (comp > IntDef.INT_ZERO) {
                        continue;
                    } else if (comp < IntDef.INT_ZERO) {
                        break;
                    }
                    // set Invoice Qty
                    PoiUtil.setCellValue(cntSheet, row, col, detail.getQty());
                    break;
                }
            }
            // Imp Customs Clearnance
            // Total Stock Qty after Customs Clearance before Imp Inbound
            col++;
            if (stockStatus.getImpStockFlag().equals(ImpStockFlag.WITH_CLEARANCE)) {
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getInRackQty());
            } else {
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            supplyFormula.append(CellReference.convertNumToColString(col - IntDef.INT_TWO));
            supplyFormula.append(row).append(StringConst.COMMA);
            // Imp W/H Stock
            // Total Import Stock Qty(Not Include NG on-hold in Warehouse)
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            formula.setLength(IntDef.INT_ZERO);
            formula.append("SUM(");
            formula.append(CellReference.convertNumToColString(col));
            formula.append(row).append(StringConst.COLON);
            formula.append(CellReference.convertNumToColString(col + IntDef.INT_TWO));
            formula.append(row).append(StringConst.RIGHT_BRACKET);
            PoiUtil.setCellFormula(cntSheet, row, col++, formula.toString());
            supplyFormula.append(CellReference.convertNumToColString(col - IntDef.INT_TWO));
            supplyFormula.append(row).append(StringConst.COMMA);
            // Total ECI on hold Stock Qty
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getEciOnholdQty());
            // Available Import Stock(Not include ECI on-hold in Warehouse)
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getImpWhsQty());
            // Already Prepared for Warehouse Outbound
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getPreparedObQty());
            // Customer Stock
            if (stockStatus.getSaCustStockFlag().equals(CustStockFlag.Y)) {
                if (stockStatus.getEndingStockDate() != null) {
                    // Total Customer Stock
                    PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getCustStockQty());
                    // Customer Stock Date
                    PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(DATE_WHITE_NORMAL));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getEndingStockDate());
                } else {
                    // Total Customer Stock
                    PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_RED_CENTER));
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustomerStock);
                    // Customer Stock Date
                    PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_RED_CENTER));
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustomerStock);
                }
            } else {
                // Total Customer Stock
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Customer Stock Date
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            // Total Supply Chain Stock Qty
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            supplyFormula.append(CellReference.convertNumToColString(col - IntDef.INT_THREE));
            supplyFormula.append(row).append(StringConst.RIGHT_BRACKET);
            PoiUtil.setCellFormula(cntSheet, row, col++, supplyFormula.toString());
            // Not In Rundown
            // Total Not In Rundown Qty
            styleKeyBuffer.setLength(IntDef.INT_ZERO);
            if (stockStatus.isHasMoreSupplier()) {
                styleKeyBuffer.append(DECIMAL_GREEN_RED_FONT).append(stockStatus.getDigit());
            } else {
                styleKeyBuffer.append(DECIMAL_WHITE_RED_FONT).append(stockStatus.getDigit());
            }
            CellStyle notInRdStyle = styleMap.get(styleKeyBuffer.toString());
            // set style
            PoiUtil.setCellStyle(cntSheet, row, col, notInRdStyle);
            formula.setLength(IntDef.INT_ZERO);
            formula.append("SUM(");
            formula.append(CellReference.convertNumToColString(col));
            formula.append(row).append(StringConst.COLON);
            formula.append(CellReference.convertNumToColString(col + IntDef.INT_TWO));
            formula.append(row).append(StringConst.RIGHT_BRACKET);
            PoiUtil.setCellFormula(cntSheet, row, col++, formula.toString());
            // Total ETD Delay Qty
            PoiUtil.setCellStyle(cntSheet, row, col, notInRdStyle);
            if (stockStatus.getEtdDelayQty() != null) {
                PoiUtil.setCellValue(cntSheet, row, col, stockStatus.getEtdDelayQty());
            }
            // Total Inbound Delay Qty
            col++;
            PoiUtil.setCellStyle(cntSheet, row, col, notInRdStyle);
            if (stockStatus.getInboundDelayQty() != null) {
                PoiUtil.setCellValue(cntSheet, row, col, stockStatus.getInboundDelayQty());
            }
            // Total Imp On Hold Qty
            col++;
            PoiUtil.setCellStyle(cntSheet, row, col, notInRdStyle);
            if (stockStatus.getNgOnholdQty() != null) {
                PoiUtil.setCellValue(cntSheet, row, col, stockStatus.getNgOnholdQty());
            }
            // reset
            CellStyle stockDayStyle = null;
            CellStyle stockBoxStyle = null;
            if (stockStatus.isHasMoreSupplier()) {
                styleKeyBuffer.setLength(IntDef.INT_ZERO);
                styleKeyBuffer.append(DECIMAL_GREEN).append(stockStatus.getDigit());
                decimalStyle = styleMap.get(styleKeyBuffer.toString());

                styleKeyBuffer.setLength(IntDef.INT_ZERO);
                styleKeyBuffer.append(DECIMAL_GREEN).append(IntDef.INT_ONE);
                stockDayStyle = styleMap.get(styleKeyBuffer.toString());

                styleKeyBuffer.setLength(IntDef.INT_ZERO);
                styleKeyBuffer.append(DECIMAL_GREEN).append(IntDef.INT_TWO);
                stockBoxStyle = styleMap.get(styleKeyBuffer.toString());
            } else {
                styleKeyBuffer.setLength(IntDef.INT_ZERO);
                styleKeyBuffer.append(DECIMAL_WHITE).append(IntDef.INT_ONE);
                stockDayStyle = styleMap.get(styleKeyBuffer.toString());

                styleKeyBuffer.setLength(IntDef.INT_ZERO);
                styleKeyBuffer.append(DECIMAL_WHITE).append(IntDef.INT_TWO);
                stockBoxStyle = styleMap.get(styleKeyBuffer.toString());
            }
            // Alarm1A
            // End of Last day Stock Qty(Exclude NG on hold Qty)
            col++;
            PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockQty1a());

            // Stock Days
            if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.Y)) {
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            } else {
                // if does not has any stock days?
                if (stockStatus.getStockDays1a() != null) {
                    // already ok
                    PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                    if (stockStatus.getNoMaster1a().equals(NoCfcFlag.N)) {
                        PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockDays1a());
                    } else {
                        StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                        stockDays.append(StringUtil.formatBigDecimal(IntDef.INT_ONE, stockStatus.getStockDays1a()));
                        PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                    }
                } else {
                    // no customer forecast
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
            }
            // No. of Boxes
            if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.Y)) {
                // if does not has any stock days?
                PoiUtil.setCellStyle(cntSheet, row, col, stockBoxStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockBoxes1a());
            } else {
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            // Min Alarm Flag
            if (stockStatus.getStockDays1a() != null || stockStatus.getStockBoxes1a() != null) {
                // get style
                if (stockStatus.isHasMoreSupplier()) {
                    alarmStyle = stockStatus.getMinAlarm1a().equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER)
                        : styleMap.get(LABEL_GREEN_RED_CENTER);
                } else {
                    alarmStyle = stockStatus.getMinAlarm1a().equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER)
                        : styleMap.get(LABEL_WHITE_RED_CENTER);
                }
                PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
                // set value
                alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG,
                    stockStatus.getMinAlarm1a());
                PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
            } else {
                // no customer forecast
                PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
            }
            // Max Alarm Flag
            if (stockStatus.getStockDays1a() != null || stockStatus.getStockBoxes1a() != null) {
                // get style
                if (stockStatus.isHasMoreSupplier()) {
                    alarmStyle = stockStatus.getMaxAlarm1a().equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER)
                        : styleMap.get(LABEL_GREEN_RED_CENTER);
                } else {
                    alarmStyle = stockStatus.getMaxAlarm1a().equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER)
                        : styleMap.get(LABEL_WHITE_RED_CENTER);
                }
                PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);

                // set value
                alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG,
                    stockStatus.getMaxAlarm1a());
                PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
            } else {
                // no customer forecast
                PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
            }
            // Next Inbound Plan
            if (stockStatus.getNextInboundDate() == null) {
                // Plan Inbound Date
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Qty
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Invoice No.
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Stock Days After Arrival Of Next Inbound
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            } else {
                // no customer forecast
                styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_NORMAL : DATE_WHITE_NORMAL;
                // Plan Inbound Date
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getNextInboundDate());
                // Qty
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getNextInboundQty());
                // Invoice No.
                styleKey = stockStatus.isHasMoreSupplier() ? LABEL_GREEN_LEFT : LABEL_WHITE_LEFT;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getNextInvoice());
                // Stock Days After Arrival Of Next Inbound
                // if does not has any stock days?
                if (stockStatus.getStockDaysPlan1a() != null) {
                    // already ok
                    PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                    if (stockStatus.getNoMasterPlan1a().equals(NoCfcFlag.N)) {
                        PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockDaysPlan1a());
                    } else {
                        StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                        stockDays.append(StringUtil.formatBigDecimal(IntDef.INT_ONE, stockStatus.getStockDaysPlan1a()));
                        PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                    }
                } else {
                    // no customer forecast
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
            }
            // Alarm1B
            if (stockStatus.getImpStockFlag().equals(ImpStockFlag.WITH_CLEARANCE)) {
                // End of Last day Stock Qty(Exclude NG on hold Qty)
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockQty1b());
                // Stock Days
                if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.Y)) {
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                } else {
                    // if does not has any stock days?
                    if (stockStatus.getStockDays1b() != null) {
                        // already ok
                        PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                        if (stockStatus.getNoMaster1b().equals(NoCfcFlag.N)) {
                            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockDays1b());
                        } else {
                            StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                            stockDays.append(StringUtil.formatBigDecimal(IntDef.INT_ONE, stockStatus.getStockDays1b()));
                            PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                        }
                    } else {
                        // no customer forecast
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                    }
                }
                // No. of Boxes
                if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.Y)) {
                    // if does not has any stock days?
                    PoiUtil.setCellStyle(cntSheet, row, col, stockBoxStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockBoxes1b());
                } else {
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                }
                // Min Alarm Flag
                if (stockStatus.getStockDays1b() != null || stockStatus.getStockBoxes1b() != null) {
                    // get style
                    if (stockStatus.isHasMoreSupplier()) {
                        alarmStyle = stockStatus.getMinAlarm1b().equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER)
                            : styleMap.get(LABEL_GREEN_RED_CENTER);
                    } else {
                        alarmStyle = stockStatus.getMinAlarm1b().equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER)
                            : styleMap.get(LABEL_WHITE_RED_CENTER);
                    }
                    PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
                    // set value
                    alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG,
                        stockStatus.getMinAlarm1b());
                    PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
                } else {
                    // no customer forecast
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
                // Max Alarm Flag
                if (stockStatus.getStockDays1b() != null || stockStatus.getStockBoxes1b() != null) {
                    // get style
                    if (stockStatus.isHasMoreSupplier()) {
                        alarmStyle = stockStatus.getMaxAlarm1b().equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER)
                            : styleMap.get(LABEL_GREEN_RED_CENTER);
                    } else {
                        alarmStyle = stockStatus.getMaxAlarm1b().equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER)
                            : styleMap.get(LABEL_WHITE_RED_CENTER);
                    }
                    PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
                    // set value
                    alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG,
                        stockStatus.getMaxAlarm1b());
                    PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
                } else {
                    // no customer forecast
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
                // Next Inbound Plan
                if (stockStatus.getNextInboundDate() == null) {
                    // Plan Inbound Date
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                    // Qty
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                    // Invoice No.
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                    // Stock Days After Arrival Of Next Inbound
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                } else {
                    // Plan Inbound Style
                    styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_NORMAL : DATE_WHITE_NORMAL;
                    // Plan Inbound Date
                    PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getNextInboundDate());
                    // Qty
                    PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getNextInboundQty());
                    // Invoice No.
                    styleKey = stockStatus.isHasMoreSupplier() ? LABEL_GREEN_LEFT : LABEL_WHITE_LEFT;
                    PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getNextInvoice());
                    // Stock Days After Arrival Of Next Inbound
                    // if does not has any stock days?
                    if (stockStatus.getStockDaysPlan1b() != null) {
                        // already ok
                        PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                        if (stockStatus.getNoMasterPlan1b().equals(NoCfcFlag.N)) {
                            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getStockDaysPlan1b());
                        } else {
                            StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                            stockDays.append(StringUtil.formatBigDecimal(IntDef.INT_ONE,
                                stockStatus.getStockDaysPlan1b()));
                            PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                        }
                    } else {
                        // no customer forecast
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                    }
                }
            } else {
                // End of Last day Stock Qty(Exclude NG on hold Qty)
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Stock Days
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // No. of Boxes
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Min Alarm Flag
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Max Alarm Flag
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Plan Inbound Date
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Qty
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Invoice No.
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Stock Days After Arrival Of Next Inbound
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            // Alarm 2
            // Target Month
            if(stockStatus.getEndDateTm() == null || !stockStatus.getEndDateTm().after(stockStatus.getStockDate())) {
                // Simulation End Date
                styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_NORMAL : DATE_WHITE_NORMAL;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getEndDateTm());
                // Shortage Date
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Min Alarm Flag 
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Max Alarm Flag
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Lowest Stock Days
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Highest Stock Days
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Lowest No. of Boxes
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Highest No. of Boxes
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            } else {
                // Simulation End Date
                styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_NORMAL : DATE_WHITE_NORMAL;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getEndDateTm());
                // Shortage Date
                if (stockStatus.getShortageDateTm() != null) {
                    styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_RED_FONT : DATE_WHITE_RED_FONT;
                    PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getShortageDateTm());
                } else {
                    if (stockStatus.getEndCfcDate() == null
                            || stockStatus.getEndCfcDate().before(stockStatus.getEndDateTm())) {
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col, noCustForecast);
                    } else {
                        styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_RED_FONT : DATE_WHITE_RED_FONT;
                        PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                    }
                    col++;
                }
                // Min Alarm Flag
                if (stockStatus.getMinAlarmTm() != null) {
                    if (!stockStatus.isHasMoreSupplier()) {
                        alarmStyle = stockStatus.getMinAlarmTm().equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER)
                            : styleMap.get(LABEL_WHITE_RED_CENTER);
                    } else {
                        alarmStyle = stockStatus.getMinAlarmTm().equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER)
                            : styleMap.get(LABEL_GREEN_RED_CENTER);
                    }
                    PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
                    // get value
                    alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG,
                        stockStatus.getMinAlarmTm());
                    PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
                } else {
                    // no customer forecast
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
                // Max Alarm Flag
                if (stockStatus.getMaxAlarmTm() != null) {
                    if (!stockStatus.isHasMoreSupplier()) {
                        alarmStyle = stockStatus.getMaxAlarmTm().equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER)
                            : styleMap.get(LABEL_WHITE_RED_CENTER);
                    } else {
                        alarmStyle = stockStatus.getMaxAlarmTm().equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER)
                            : styleMap.get(LABEL_GREEN_RED_CENTER);
                    }
                    PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
                    // get value
                    alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG,
                        stockStatus.getMaxAlarmTm());
                    PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
                } else {
                    // no customer forecast
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
                // Lowest Stock Days
                // Highest Stock Days
                if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.Y)) {
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                } else {
                    // Lowest Stock Days
                    // if does not has any stock days?
                    if (stockStatus.getMinStockDayTm() != null) {
                        // already exist
                        PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                        if (stockStatus.getNoMasterMinTm().equals(NoCfcFlag.N)) {
                            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMinStockDayTm());
                        } else {
                            StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                            stockDays
                                .append(StringUtil.formatBigDecimal(IntDef.INT_ONE, stockStatus.getMinStockDayTm()));
                            PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                        }
                    } else {
                        // no customer forecast
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                    }
                    // Highest Stock Days
                    // if does not has any stock days?
                    if (stockStatus.getMaxStockDayTm() != null) {
                        // already exist
                        PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                        if (stockStatus.getNoMasterMaxTm().equals(NoCfcFlag.N)) {
                            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMaxStockDayTm());
                        } else {
                            StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                            stockDays
                                .append(StringUtil.formatBigDecimal(IntDef.INT_ONE, stockStatus.getMaxStockDayTm()));
                            PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                        }
                    } else {
                        // no customer forecast
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                    }
                }
                // Lowest No. of Boxes
                // Highest No. of Boxes
                if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.N)) {
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                    PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                } else {
                    // Lowest No. of Boxes
                    // if does not has any stock days?
                    if (stockStatus.getMinBoxesTm() != null) {
                        // already exist
                        PoiUtil.setCellStyle(cntSheet, row, col, stockBoxStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMinBoxesTm());
                    } else {
                        // no customer forecast
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                    }
                    // Highest No. of Boxes
                    // if does not has any stock days?
                    if (stockStatus.getMaxBoxesTm() != null) {
                        // already exist
                        PoiUtil.setCellStyle(cntSheet, row, col, stockBoxStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMaxBoxesTm());
                    } else {
                        // no customer forecast
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                    }
                }
            }
            // Part Remark(In Master)
            styleKey = stockStatus.isHasMoreSupplier() ? LABEL_GREEN_LEFT : LABEL_WHITE_LEFT;
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getPartsRemark());
            // Rundown Remark
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getRundownRemark());
            
            // for forecast 1
            for (int i = IntDef.INT_ONE; i <= maxFcNum; i++) {
                // prepare
                prepareForecastAlarm2(cntSheet, stockStatus, lang, styleMap, row, col, i);
            }
            // get latest cool
            col = col + maxFcNum * IntDef.INT_EIGHT;
            // Alarm 3: To Verify Order Calculation
            if (stockStatus.getEndDate3() != null) {
                // Simulation on End Date (Until End of Target month)
                styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_NORMAL : DATE_WHITE_NORMAL;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getEndDate3());
                // Shortage Date
                if (stockStatus.getShortageDate3() != null) {
                    styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_RED_FONT : DATE_WHITE_RED_FONT;
                    PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getShortageDate3());
                } else {
                    // check is has no customer forecast?
                    if (stockStatus.getEndCfcDate() == null
                            || stockStatus.getEndCfcDate().before(stockStatus.getEndDate3())) {
                        PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                        PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                    } else {
                        styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_RED_FONT : DATE_WHITE_RED_FONT;
                        PoiUtil.setCellStyle(cntSheet, row, col++, styleMap.get(styleKey));
                    }
                }
                // Ending Stock Days (Until Last Day of Target Month)
                if (stockStatus.getStockDay3() != null) {
                    // set style
                    PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                    // set value
                    StringBuffer stockDays = new StringBuffer();
                    if (stockStatus.getNoMaster3().equals(NoCfcFlag.Y)) {
                        stockDays.append(StringConst.GREATER).append(StringConst.BLANK);
                    }
                    stockDays.append(StringUtil.formatBigDecimal(IntDef.INT_ONE, stockStatus.getStockDay3()));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                } else {
                    // check is has no order forecast?
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
                // Safety Stock Flag
                if (stockStatus.getSafetyAlarm3() != null) {
                    // get style
                    if (!stockStatus.isHasMoreSupplier()) {
                        alarmStyle = stockStatus.getSafetyAlarm3().equals(AlarmFlag.N) ? styleMap
                            .get(LABEL_WHITE_CENTER) : styleMap.get(LABEL_WHITE_RED_CENTER);
                    } else {
                        alarmStyle = stockStatus.getSafetyAlarm3().equals(AlarmFlag.N) ? styleMap
                            .get(LABEL_GREEN_CENTER) : styleMap.get(LABEL_GREEN_RED_CENTER);
                    }
                    PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
                    // get value
                    alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG,
                        stockStatus.getSafetyAlarm3());
                    PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
                } else {
                    // check is has no order forecast?
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
                // Safety Stock Days For Alarm 3
                PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getOrderSafetyStock());
                // Add On Qty to Avoid Shortage Until End of Target Month
                if (stockStatus.getShortageAddOn3() != null) {
                    PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getShortageAddOn3());
                } else {
                    // check is has no order forecast?
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
                // Add On Qty to Achieve Safety Stock Level Until End of Target Month
                if (stockStatus.getSafetyAddOn3() != null) {
                    PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getSafetyAddOn3());
                } else {
                    // check is has no order forecast?
                    PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                    PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                }
            } else {
                // set all with gray style
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            // Alarm 4: Actual Outbound Fluctuation
            if (stockStatus.getTotalCfcQty4() != null) {
                // Actual Outbound Over Total Monthly Plan and Over Agreed Fluctuation Ratio Date
                styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_RED_FONT : DATE_WHITE_RED_FONT;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getOverRatioDate4());
                // Comparison of Actual Outbound vs. Monthly Plan
                styleKey = stockStatus.isHasMoreSupplier() ? DECIMAL_PERCENTAGE_GREEN : DECIMAL_PERCENTAGE;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++,
                    DecimalUtil.divide(stockStatus.getFluctuationRatio4(), new BigDecimal(IntDef.INT_HUNDRED)));
                // The Gap between Actual Outbound and Monthly Plan (Current month)
                PoiUtil.setCellStyle(cntSheet, row, col, decimalStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getGapValue4());
            } else {
                // Actual Outbound Over Total Monthly Plan and Over Agreed Fluctuation Ratio Date
                PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                // Comparison of Actual Outbound vs. Monthly Plan
                PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
                // The Gap between Actual Outbound and Monthly Plan (Current month)
                PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, noCustForecast);
            }
            // Alarm Master Data
            if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.Y)) {
                // Min Stock Days
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Max Stock Days
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Min Stock Boxes
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(DECIMAL_WHITE + IntDef.INT_TWO));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMinBox());
                // Max Stock Boxes
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(DECIMAL_WHITE + IntDef.INT_TWO));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMaxBox());
            } else {
                // Min Stock Days
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(DECIMAL_WHITE + IntDef.INT_ONE));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMinStock());
                // Max Stock Days
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(DECIMAL_WHITE + IntDef.INT_ONE));
                PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getMaxStock());
                // Min Stock Boxes
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
                // Max Stock Boxes
                PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            }
            // Include Customer Stock In Stock Status and Rundown Flag
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_CENTER));
            String stockFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.CUSTOMER_STOCK_FLAG,
                stockStatus.getSaCustStockFlag());
            PoiUtil.setCellValue(cntSheet, row, col++, stockFlag);
            // Imp Outbound Plan Input
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(DATE_WHITE_NORMAL));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getLastObActualDate());
            // Build Out Data
            // Build-out Flag
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_CENTER));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getBuildOutFlag());
            // Build-out Month
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(MONTH_PREFIX));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getBuildOutMonth());
            // Last TTC Imp Order Month
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(MONTH_PREFIX));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getLastOrderMonth());
            // Last Supplier Delivery Month
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(MONTH_PREFIX));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getLastDeliveryMonth());
            // Discontinue Indicator
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(LABEL_WHITE_CENTER));
            PoiUtil.setCellValue(cntSheet, row, col++, stockStatus.getInActiveFlag());
            // reset
            row++;
            col = IntDef.INT_ONE;
        }
        // set download time:
        row++;
        // set download time
        String targetFormat = lang.equals(Language.CHINESE) ? DateTimeUtil.FORMAT_DATE_DDMMMYYYYHHMMSS
            : DateTimeUtil.FORMAT_DDMMMYYYYHHMMSS;
        String dateStr = DateTimeUtil.formatDate(targetFormat, dateList[IntDef.INT_ZERO]);
        String timeStr = MessageManager.getMessage(DOWNLOAD_TIME, lang.getName());
        timeStr = MessageFormat.format(timeStr, new Object[] { dateStr, office });
        PoiUtil.setCellStyle(cntSheet, row, IntDef.INT_ONE, styleMap.get(NORMAL_LABEL_WO_LINE));
        PoiUtil.setCellValue(cntSheet, row++, IntDef.INT_ONE, timeStr);
        // set last office data time of SSMS
        if (dateList[IntDef.INT_ONE] != null) {
            dateStr = DateTimeUtil.formatDate(targetFormat, dateList[IntDef.INT_ONE]);
            timeStr = MessageManager.getMessage(SYNC_SSMS_DATE_TIME, lang.getName());
            timeStr = MessageFormat.format(timeStr, new Object[] { dateStr });
            PoiUtil.setCellStyle(cntSheet, row, IntDef.INT_ONE, styleMap.get(NORMAL_LABEL_WO_LINE));
            PoiUtil.setCellValue(cntSheet, row++, IntDef.INT_ONE, timeStr);
        }
        // set last office data time of V-V
        if (dateList[IntDef.INT_TWO] != null) {
            dateStr = DateTimeUtil.formatDate(targetFormat, dateList[IntDef.INT_TWO]);
            timeStr = MessageManager.getMessage(SYNC_VV_DATE_TIME, lang.getName());
            timeStr = MessageFormat.format(timeStr, new Object[] { office, dateStr });
            PoiUtil.setCellStyle(cntSheet, row, IntDef.INT_ONE, styleMap.get(NORMAL_LABEL_WO_LINE));
            PoiUtil.setCellValue(cntSheet, row++, IntDef.INT_ONE, timeStr);
        }
        // set last office data time of AISIN
        if (dateList[IntDef.INT_THREE] != null) {
            dateStr = DateTimeUtil.formatDate(targetFormat, dateList[IntDef.INT_THREE]);
            timeStr = MessageManager.getMessage(SYNC_AISIN_DATE_TIME, lang.getName());
            timeStr = MessageFormat.format(timeStr, new Object[] { office, dateStr });
            PoiUtil.setCellStyle(cntSheet, row, IntDef.INT_ONE, styleMap.get(NORMAL_LABEL_WO_LINE));
            PoiUtil.setCellValue(cntSheet, row, IntDef.INT_ONE, timeStr);
        }
        // remove style sheet
        workbook.removeSheetAt(IntDef.INT_ONE);
        workbook.setForceFormulaRecalculation(true);
    }

    /**
     * prepare forecast alarm 2.
     *
     * @param cntSheet current sheet
     * @param stockStatus stock status
     * @param lang language
     * @param styleMap style map
     * @param row row
     * @param strcol start col
     * @param forecastNum forecast number
     */
    private static void prepareForecastAlarm2(Sheet cntSheet, TntStockStatusEx stockStatus, Language lang,
        Map<String, CellStyle> styleMap, int row, int strcol, int forecastNum) {

        // set col
        int col = strcol + ((forecastNum - IntDef.INT_ONE) * IntDef.INT_EIGHT);
        // get no master style
        CellStyle labelGrayStyle = styleMap.get(LABEL_GRAY15);
        // not belongs to current parts
        if (forecastNum > stockStatus.getForecastNum().intValue()) {
            // set cell style
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);

            // return
            return;
        }

        // String
        String styleKey = null;
        // get no order forecast
        String noOrderForecast = MessageManager.getMessage(LABEL_NOORDERFORECAST, lang.getName());
        // get no customer forecast
        String noCustForecast = MessageManager.getMessage(LABEL_NOCUSTOMERFORECAST, lang.getName());
        // get no customer & order forecast
        String noAllForecast = MessageManager.getMessage(LABEL_NOORDERCUSTOMERFORECAST, lang.getName());
        // set no master style
        CellStyle noMasterStyle = null;
        CellStyle stockDayStyle = null;
        CellStyle stockBoxStyle = null;
        if (stockStatus.isHasMoreSupplier()) {
            stockDayStyle = styleMap.get(DECIMAL_WHITE + IntDef.INT_ONE);
            stockBoxStyle = styleMap.get(DECIMAL_GREEN + IntDef.INT_TWO);
            noMasterStyle = styleMap.get(LABEL_GREEN_RED_CENTER);
        } else {
            stockDayStyle = styleMap.get(DECIMAL_WHITE + IntDef.INT_ONE);
            stockBoxStyle = styleMap.get(DECIMAL_WHITE + IntDef.INT_TWO);
            noMasterStyle = styleMap.get(LABEL_WHITE_RED_CENTER);
        }

        // defined get values
        Date endDate = null;
        Date shortageDate = null;
        Integer noCfcFlag = null;
        Integer noPfcFlag = null;
        Integer minAlarm = null;
        Integer maxAlarm = null;
        BigDecimal minStockDay = null;
        BigDecimal maxStockDay = null;
        BigDecimal minStockBoxes = null;
        BigDecimal maxStockBoxes = null;
        Integer noPfcMinFlag = null;
        Integer noPfcMaxFlag = null;

        switch (forecastNum) {
            case IntDef.INT_ONE:
                endDate = stockStatus.getEndDateF1();
                shortageDate = stockStatus.getShortageDateF1();
                noCfcFlag = stockStatus.getNoCfcFlagF1();
                noPfcFlag = stockStatus.getNoPfcFlagF1();
                minAlarm = stockStatus.getMinAlarmF1();
                maxAlarm = stockStatus.getMaxAlarmF1();
                minStockDay = stockStatus.getMinStockDayF1();
                maxStockDay = stockStatus.getMaxStockDayF1();
                minStockBoxes = stockStatus.getMinBoxesF1();
                maxStockBoxes = stockStatus.getMaxBoxesF1();
                noPfcMinFlag = stockStatus.getNoPfcMinF1();
                noPfcMaxFlag = stockStatus.getNoPfcMaxF1();
                break;
            case IntDef.INT_TWO:
                endDate = stockStatus.getEndDateF2();
                shortageDate = stockStatus.getShortageDateF2();
                noCfcFlag = stockStatus.getNoCfcFlagF2();
                noPfcFlag = stockStatus.getNoPfcFlagF2();
                minAlarm = stockStatus.getMinAlarmF2();
                maxAlarm = stockStatus.getMaxAlarmF2();
                minStockDay = stockStatus.getMinStockDayF2();
                maxStockDay = stockStatus.getMaxStockDayF2();
                minStockBoxes = stockStatus.getMinBoxesF2();
                maxStockBoxes = stockStatus.getMaxBoxesF2();
                noPfcMinFlag = stockStatus.getNoPfcMinF2();
                noPfcMaxFlag = stockStatus.getNoPfcMaxF2();
                break;
            case IntDef.INT_THREE:
                endDate = stockStatus.getEndDateF3();
                shortageDate = stockStatus.getShortageDateF3();
                noCfcFlag = stockStatus.getNoCfcFlagF3();
                noPfcFlag = stockStatus.getNoPfcFlagF3();
                minAlarm = stockStatus.getMinAlarmF3();
                maxAlarm = stockStatus.getMaxAlarmF3();
                minStockDay = stockStatus.getMinStockDayF3();
                maxStockDay = stockStatus.getMaxStockDayF3();
                minStockBoxes = stockStatus.getMinBoxesF3();
                maxStockBoxes = stockStatus.getMaxBoxesF3();
                noPfcMinFlag = stockStatus.getNoPfcMinF3();
                noPfcMaxFlag = stockStatus.getNoPfcMaxF3();
                break;
            case IntDef.INT_FOUR:
                endDate = stockStatus.getEndDateF4();
                shortageDate = stockStatus.getShortageDateF4();
                noCfcFlag = stockStatus.getNoCfcFlagF4();
                noPfcFlag = stockStatus.getNoPfcFlagF4();
                minAlarm = stockStatus.getMinAlarmF4();
                maxAlarm = stockStatus.getMaxAlarmF4();
                minStockDay = stockStatus.getMinStockDayF4();
                maxStockDay = stockStatus.getMaxStockDayF4();
                minStockBoxes = stockStatus.getMinBoxesF4();
                maxStockBoxes = stockStatus.getMaxBoxesF4();
                noPfcMinFlag = stockStatus.getNoPfcMinF4();
                noPfcMaxFlag = stockStatus.getNoPfcMaxF4();
                break;
            case IntDef.INT_FIVE:
                endDate = stockStatus.getEndDateF5();
                shortageDate = stockStatus.getShortageDateF5();
                noCfcFlag = stockStatus.getNoCfcFlagF5();
                noPfcFlag = stockStatus.getNoPfcFlagF5();
                minAlarm = stockStatus.getMinAlarmF5();
                maxAlarm = stockStatus.getMaxAlarmF5();
                minStockDay = stockStatus.getMinStockDayF5();
                maxStockDay = stockStatus.getMaxStockDayF5();
                minStockBoxes = stockStatus.getMinBoxesF5();
                maxStockBoxes = stockStatus.getMaxBoxesF5();
                noPfcMinFlag = stockStatus.getNoPfcMinF5();
                noPfcMaxFlag = stockStatus.getNoPfcMaxF5();
                break;
            case IntDef.INT_SIX:
                endDate = stockStatus.getEndDateF6();
                shortageDate = stockStatus.getShortageDateF6();
                noCfcFlag = stockStatus.getNoCfcFlagF6();
                noPfcFlag = stockStatus.getNoPfcFlagF6();
                minAlarm = stockStatus.getMinAlarmF6();
                maxAlarm = stockStatus.getMaxAlarmF6();
                minStockDay = stockStatus.getMinStockDayF6();
                maxStockDay = stockStatus.getMaxStockDayF6();
                minStockBoxes = stockStatus.getMinBoxesF6();
                maxStockBoxes = stockStatus.getMaxBoxesF6();
                noPfcMinFlag = stockStatus.getNoPfcMinF6();
                noPfcMaxFlag = stockStatus.getNoPfcMaxF6();
                break;
        }

        // not belongs to current parts
        if (endDate == null) {

            // set cell style
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);

            // return
            return;
        }
        
        // if is last date
        if (!endDate.after(stockStatus.getStockDate())) {
            // Simulation End Date
            styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_NORMAL : DATE_WHITE_NORMAL;
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
            PoiUtil.setCellValue(cntSheet, row, col++, endDate);
            // set cell style
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);

            return;
        }

        // get no master reason
        String reason = null;
        if (noCfcFlag.equals(NoCfcFlag.Y) && noPfcFlag.equals(NoPfcFlag.Y)) {
            reason = noAllForecast;
        } else if (noCfcFlag.equals(NoCfcFlag.Y)) {
            reason = noCustForecast;
        } else if (noPfcFlag.equals(NoPfcFlag.Y)) {
            reason = noOrderForecast;
        }

        // Simulation End Date
        styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_NORMAL : DATE_WHITE_NORMAL;
        PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
        PoiUtil.setCellValue(cntSheet, row, col++, endDate);

        // Shortage Date
        if (shortageDate != null) {
            styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_RED_FONT : DATE_WHITE_RED_FONT;
            PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
            PoiUtil.setCellValue(cntSheet, row, col++, shortageDate);
        } else {
            // check is has no order forecast?
            if (reason != null) {
                PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, reason);
            } else {
                styleKey = stockStatus.isHasMoreSupplier() ? DATE_GREEN_RED_FONT : DATE_WHITE_RED_FONT;
                PoiUtil.setCellStyle(cntSheet, row, col++, styleMap.get(styleKey));
            }
        }
        // Min Alarm Flag
        if (minAlarm != null) {
            CellStyle alarmStyle = null;
            if (!stockStatus.isHasMoreSupplier()) {
                alarmStyle = minAlarm.equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER) : styleMap
                    .get(LABEL_WHITE_RED_CENTER);
            } else {
                alarmStyle = minAlarm.equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER) : styleMap
                    .get(LABEL_GREEN_RED_CENTER);
            }
            PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
            // get value
            String alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG, minAlarm);
            PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
        } else {
            // check is has no order forecast?
            PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, reason);
        }
        // Max Alarm Flag
        if (maxAlarm != null) {
            CellStyle alarmStyle = null;
            if (!stockStatus.isHasMoreSupplier()) {
                alarmStyle = maxAlarm.equals(AlarmFlag.N) ? styleMap.get(LABEL_WHITE_CENTER) : styleMap
                    .get(LABEL_WHITE_RED_CENTER);
            } else {
                alarmStyle = maxAlarm.equals(AlarmFlag.N) ? styleMap.get(LABEL_GREEN_CENTER) : styleMap
                    .get(LABEL_GREEN_RED_CENTER);
            }
            PoiUtil.setCellStyle(cntSheet, row, col, alarmStyle);
            // get value
            String alarmFlag = CodeCategoryManager.getCodeName(lang.getCode(), CodeMasterCategory.ALARM_FLAG, maxAlarm);
            PoiUtil.setCellValue(cntSheet, row, col++, alarmFlag);
        } else {
            // check is has no order forecast?
            PoiUtil.setCellStyle(cntSheet, row, col, noMasterStyle);
            PoiUtil.setCellValue(cntSheet, row, col++, reason);
        }
        // Lowest Stock Days
        // Highest Stock Days
        if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.Y)) {
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
        } else {
            // Lowest Stock Days
            // if does not has any stock days?
            if (minStockDay != null) {
                // already exist
                PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                if (noPfcMinFlag.equals(NoCfcFlag.N)) {
                    PoiUtil.setCellValue(cntSheet, row, col++, minStockDay);
                } else {
                    StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                    stockDays.append(StringUtil.formatBigDecimal(IntDef.INT_ONE, minStockDay));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                }
            } else {
                // no order/customer forecast
                styleKey = stockStatus.isHasMoreSupplier() ? LABEL_GREEN_RED_CENTER : LABEL_WHITE_RED_CENTER;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, reason);
            }
            // Highest Stock Days
            // if does not has any stock days?
            if (maxStockDay != null) {
                // already exist
                PoiUtil.setCellStyle(cntSheet, row, col, stockDayStyle);
                if (noPfcMaxFlag.equals(NoCfcFlag.N)) {
                    PoiUtil.setCellValue(cntSheet, row, col++, maxStockDay);
                } else {
                    StringBuffer stockDays = new StringBuffer(StringConst.GREATER).append(StringConst.BLANK);
                    stockDays.append(StringUtil.formatBigDecimal(IntDef.INT_ONE, maxStockDay));
                    PoiUtil.setCellValue(cntSheet, row, col++, stockDays.toString());
                }
            } else {
                // no order/customer forecast
                styleKey = stockStatus.isHasMoreSupplier() ? LABEL_GREEN_RED_CENTER : LABEL_WHITE_RED_CENTER;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, reason);
            }
        }
        // Lowest No. of Boxes
        // Highest No. of Boxes
        if (stockStatus.getInventoryBoxFlag().equals(InventoryByBox.N)) {
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
            PoiUtil.setCellStyle(cntSheet, row, col++, labelGrayStyle);
        } else {
            // Lowest No. of Boxes
            // if does not has any stock days?
            if (minStockBoxes != null) {
                // already exist
                PoiUtil.setCellStyle(cntSheet, row, col, stockBoxStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, minStockBoxes);
            } else {
                // no order/customer forecast
                styleKey = stockStatus.isHasMoreSupplier() ? LABEL_GREEN_RED_CENTER : LABEL_WHITE_RED_CENTER;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, reason);
            }

            // Highest No. of Boxes
            // if does not has any stock days?
            if (maxStockBoxes != null) {
                // already exist
                PoiUtil.setCellStyle(cntSheet, row, col, stockBoxStyle);
                PoiUtil.setCellValue(cntSheet, row, col++, maxStockBoxes);
            } else {
                // no order/customer forecast
                styleKey = stockStatus.isHasMoreSupplier() ? LABEL_GREEN_RED_CENTER : LABEL_WHITE_RED_CENTER;
                PoiUtil.setCellStyle(cntSheet, row, col, styleMap.get(styleKey));
                PoiUtil.setCellValue(cntSheet, row, col++, reason);
            }
        }
    }
}