package com.quotation.core.util.interceptor;

/**
 * Hold default value for screen, for example: filter width.
 * 
 * @author liu_bing
 */
public final class ScreenDefault {
    private static ScreenDefault instance = new ScreenDefault();

    /**
     * Default width of Input Text in list page.
     */
    private String filterInputWidth = "98%";

    /**
     * Default width of Input Text in detail page.
     */
    private String detailInputWidth = "97%";

    /**
     * Default width of Input Text(colspan=2) in detail page.
     */
    private String detailInputWidthTwo = "98%";

    /**
     * Default width of Input Text(colspan=3) in detail page.
     */
    private String detailInputWidthTrhee = "99%";

    /**
     * Default width of pop up window for select customer parts.
     */
    private String selectCustomerPartsPopWidth = "1000";

    /**
     * Default height of pop up window for select customer parts.
     */
    private String selectCustomerPartsPopHeight = "500";

    /**
     * Default width of pop up window for select supplier parts.
     */
    private String selectSupplierPartsPopWidth = "1000";

    /**
     * Default height of pop up window for select supplier parts.
     */
    private String selectSupplierPartsPopHeight = "500";

    /**
     * Default width of pop up window for select parts master.
     */
    private String selectPartsMasterPopWidth = "850";

    /**
     * Default height of pop up window for select parts master.
     */
    private String selectPartsMasterPopHeight = "500";

    /**
     * The Constructors Method.
     * 
     * 
     * @author liu_bing
     */
    private ScreenDefault() {}

    /**
     * 
     * ScreenDefault
     * 
     * @return ScreenDefaults
     * 
     * @author liu_bing
     */
    public static final ScreenDefault getInstance() {
        return instance;
    }

    /**
     * Get the filterInputWidth.
     * 
     * @return filterInputWidth
     * 
     * @author liu_bing
     */
    public String getFilterInputWidth() {
        return this.filterInputWidth;
    }

    /**
     * Get the detailInputWidth.
     * 
     * @return detailInputWidth
     * 
     * @author liu_bing
     */
    public String getDetailInputWidth() {
        return this.detailInputWidth;
    }

    /**
     * Get the detailInputWidthTwo.
     * 
     * @return detailInputWidthTwo
     * 
     * @author liu_bing
     */
    public String getDetailInputWidthTwo() {
        return this.detailInputWidthTwo;
    }

    /**
     * Get the detailInputWidthTrhee.
     * 
     * @return detailInputWidthTrhee
     * 
     * @author liu_bing
     */
    public String getDetailInputWidthTrhee() {
        return this.detailInputWidthTrhee;
    }

    /**
     * Get the selectCustomerPartsPopWidth.
     * 
     * @return selectCustomerPartsPopWidth
     * 
     * @author liu_bing
     */
    public String getSelectCustomerPartsPopWidth() {
        return this.selectCustomerPartsPopWidth;
    }

    /**
     * Get the selectCustomerPartsPopHeight.
     * 
     * @return selectCustomerPartsPopHeight
     * 
     * @author liu_bing
     */
    public String getSelectCustomerPartsPopHeight() {
        return this.selectCustomerPartsPopHeight;
    }

    /**
     * Get the selectSupplierPartsPopWidth.
     * 
     * @return selectSupplierPartsPopWidth
     * 
     * @author liu_bing
     */
    public String getSelectSupplierPartsPopWidth() {
        return this.selectSupplierPartsPopWidth;
    }

    /**
     * Get the selectSupplierPartsPopHeight.
     * 
     * @return selectSupplierPartsPopHeight
     * 
     * @author liu_bing
     */
    public String getSelectSupplierPartsPopHeight() {
        return this.selectSupplierPartsPopHeight;
    }

    /**
     * Get the selectPartsMasterPopWidth.
     * 
     * @return selectPartsMasterPopWidth
     * 
     * @author liu_bing
     */
    public String getSelectPartsMasterPopWidth() {
        return this.selectPartsMasterPopWidth;
    }

    /**
     * Get the selectPartsMasterPopHeight.
     * 
     * @return selectPartsMasterPopHeight
     * 
     * @author liu_bing
     */
    public String getSelectPartsMasterPopHeight() {
        return this.selectPartsMasterPopHeight;
    }
}
