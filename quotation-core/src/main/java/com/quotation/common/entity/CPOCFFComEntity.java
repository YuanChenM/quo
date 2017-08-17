/**
 * CPOCFFComEntity.java
 * 
 * @screen CPOCFF03, CPOCFF04, CPOCFF05
 * 
 */
package com.quotation.common.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Upload Data Entity.
 */
public class CPOCFFComEntity extends TnmPartsMaster {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** cfcMonthId */
    private Integer cfcMonthId;

    /** cfcMonthId */
    private Integer cfcId;

    /** cfcMonth */
    private String cfcMonth;

    /** cfcQty */
    private BigDecimal cfcQty;
    
    /** cfcMonthQty */
    private BigDecimal cfcMonthQty;

    /** fcDate */
    private Date fcDate;

    /** firstFcMonth */
    private String firstFcMonth;

    /** lastFcMonth */
    private String lastFcMonth;

    /** cfcNo */
    private String cfcNo;
    
    /** cfcDate */
    private Date cfcDate;

    /** totalAdjDays */
    private int totalAdjDays;
    
    /** workingFlg */
    private Integer workingFlg;

    /** adjustDateLst */
    private List<CPOCFFComDailyEntity> adjustDateLst;

    /** cfcMonthlyLst */
    private List<CPOCFFComMonthlyEntity> cfcMonthlyLst;
    
    /** cfcFcMapLst */
    private List<LinkedHashMap<Date, BigDecimal>> cfcFcMapLst;
    
    /** tntRundownCfcLst */
    private List<TntRundownCfc> tntRundownCfcLst; 
    
    /** cpocffTEMPEntityLst */
    private List<CPOCFFTEMPEntity> cpocffTEMPEntityLst;
    
    /** cfcMonthQty */
    private LinkedHashMap<String, BigDecimal> cfcMonthQtyMap;
    
    

    /**
     * Get the firstFcMonth.
     *
     * @return firstFcMonth
     */
    public String getFirstFcMonth() {
        return this.firstFcMonth;
    }

    /**
     * Set the firstFcMonth.
     *
     * @param firstFcMonth firstFcMonth
     */
    public void setFirstFcMonth(String firstFcMonth) {
        this.firstFcMonth = firstFcMonth;
    }

    /**
     * Get the lastFcMonth.
     *
     * @return lastFcMonth
     */
    public String getLastFcMonth() {
        return this.lastFcMonth;
    }

    /**
     * Set the lastFcMonth.
     *
     * @param lastFcMonth lastFcMonth
     */
    public void setLastFcMonth(String lastFcMonth) {
        this.lastFcMonth = lastFcMonth;
    }

    /**
     * Get the cfcNo.
     *
     * @return cfcNo
     */
    public String getCfcNo() {
        return this.cfcNo;
    }

    /**
     * Set the cfcNo.
     *
     * @param cfcNo cfcNo
     */
    public void setCfcNo(String cfcNo) {
        this.cfcNo = cfcNo;
    }

    /**
     * Get the cfcMonthId.
     *
     * @return cfcMonthId
     */
    public Integer getCfcMonthId() {
        return this.cfcMonthId;
    }

    /**
     * Set the cfcMonthId.
     *
     * @param cfcMonthId cfcMonthId
     */
    public void setCfcMonthId(Integer cfcMonthId) {
        this.cfcMonthId = cfcMonthId;
    }

    /**
     * Get the cfcId.
     *
     * @return cfcId
     */
    public Integer getCfcId() {
        return this.cfcId;
    }

    /**
     * Set the cfcId.
     *
     * @param cfcId cfcId
     */
    public void setCfcId(Integer cfcId) {
        this.cfcId = cfcId;
    }

    /**
     * Get the cfcMonth.
     *
     * @return cfcMonth
     */
    public String getCfcMonth() {
        return this.cfcMonth;
    }

    /**
     * Set the cfcMonth.
     *
     * @param cfcMonth cfcMonth
     */
    public void setCfcMonth(String cfcMonth) {
        this.cfcMonth = cfcMonth;
    }

    /**
     * Get the cfcQty.
     *
     * @return cfcQty
     */
    public BigDecimal getCfcQty() {
        return this.cfcQty;
    }

    /**
     * Set the cfcQty.
     *
     * @param cfcQty cfcQty
     */
    public void setCfcQty(BigDecimal cfcQty) {
        this.cfcQty = cfcQty;
    }

    /**
     * Get the fcDate.
     *
     * @return fcDate
     */
    public Date getFcDate() {
        return this.fcDate;
    }

    /**
     * Set the fcDate.
     *
     * @param fcDate fcDate
     */
    public void setFcDate(Date fcDate) {
        this.fcDate = fcDate;
    }

    /**
     * Get the totalAdjDays.
     *
     * @return totalAdjDays
     */
    public int getTotalAdjDays() {
        return this.totalAdjDays;
    }

    /**
     * Set the totalAdjDays.
     *
     * @param totalAdjDays totalAdjDays
     */
    public void setTotalAdjDays(int totalAdjDays) {
        this.totalAdjDays = totalAdjDays;
    }

    /**
     * Get the adjustDateLst.
     *
     * @return adjustDateLst
     */
    public List<CPOCFFComDailyEntity> getAdjustDateLst() {
        return this.adjustDateLst;
    }

    /**
     * Set the adjustDateLst.
     *
     * @param adjustDateLst adjustDateLst
     */
    public void setAdjustDateLst(List<CPOCFFComDailyEntity> adjustDateLst) {
        this.adjustDateLst = adjustDateLst;
    }

    /**
     * Get the cfcMonthlyLst.
     *
     * @return cfcMonthlyLst
     */
    public List<CPOCFFComMonthlyEntity> getCfcMonthlyLst() {
        return this.cfcMonthlyLst;
    }

    /**
     * Set the cfcMonthlyLst.
     *
     * @param cfcMonthlyLst cfcMonthlyLst
     */
    public void setCfcMonthlyLst(List<CPOCFFComMonthlyEntity> cfcMonthlyLst) {
        this.cfcMonthlyLst = cfcMonthlyLst;
    }

    /**
     * Get the cfcFcMapLst.
     *
     * @return cfcFcMapLst
     */
    public List<LinkedHashMap<Date, BigDecimal>> getCfcFcMapLst() {
        return this.cfcFcMapLst;
    }

    /**
     * Set the cfcFcMapLst.
     *
     * @param cfcFcMapLst cfcFcMapLst
     */
    public void setCfcFcMapLst(List<LinkedHashMap<Date, BigDecimal>> cfcFcMapLst) {
        this.cfcFcMapLst = cfcFcMapLst;
    }

    /**
     * Get the tntRundownCfcLst.
     *
     * @return tntRundownCfcLst
     */
    public List<TntRundownCfc> getTntRundownCfcLst() {
        return this.tntRundownCfcLst;
    }

    /**
     * Set the tntRundownCfcLst.
     *
     * @param tntRundownCfcLst tntRundownCfcLst
     */
    public void setTntRundownCfcLst(List<TntRundownCfc> tntRundownCfcLst) {
        this.tntRundownCfcLst = tntRundownCfcLst;
    }

    /**
     * Get the cfcMonthQtyMap.
     *
     * @return cfcMonthQtyMap
     */
    public LinkedHashMap<String, BigDecimal> getCfcMonthQtyMap() {
        return this.cfcMonthQtyMap;
    }

    /**
     * Set the cfcMonthQtyMap.
     *
     * @param cfcMonthQtyMap cfcMonthQtyMap
     */
    public void setCfcMonthQtyMap(LinkedHashMap<String, BigDecimal> cfcMonthQtyMap) {
        this.cfcMonthQtyMap = cfcMonthQtyMap;
    }

    /**
     * Get the cpocffTEMPEntityLst.
     *
     * @return cpocffTEMPEntityLst
     */
    public List<CPOCFFTEMPEntity> getCpocffTEMPEntityLst() {
        return this.cpocffTEMPEntityLst;
    }

    /**
     * Set the cpocffTEMPEntityLst.
     *
     * @param cpocffTEMPEntityLst cpocffTEMPEntityLst
     */
    public void setCpocffTEMPEntityLst(List<CPOCFFTEMPEntity> cpocffTEMPEntityLst) {
        this.cpocffTEMPEntityLst = cpocffTEMPEntityLst;
    }

    public Date getCfcDate() {
        return this.cfcDate;
    }

    public void setCfcDate(Date cfcDate) {
        this.cfcDate = cfcDate;
    }

    public BigDecimal getCfcMonthQty() {
        return this.cfcMonthQty;
    }

    public void setCfcMonthQty(BigDecimal cfcMonthQty) {
        this.cfcMonthQty = cfcMonthQty;
    }

    public Integer getWorkingFlg() {
        return this.workingFlg;
    }

    public void setWorkingFlg(Integer workingFlg) {
        this.workingFlg = workingFlg;
    }





}