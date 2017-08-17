/**
 * @screen common
 * 
 */
package com.quotation.common.bean;

import com.quotation.common.entity.TntRdAttachCfc;

import java.util.Date;
import java.util.List;

/**
 * TntRundownMasterEx.
 */
public class TntRundownHeader extends com.quotation.core.base.BaseEntity {

    /** serialVersionUID */
    private static final long serialVersionUID = -1208221433312269051L;

    /** stock Date */
    private Date stockDate;

    /** latest rundown end date */
    private Date latestRunEndDate;

    /** shareList */
    private List<TntRdAttachCfc> shareList;

    /** notInRundownList */
    private List<TntNotInRundownEx> notInRundownList;

    /** rdDetailAttachList */
    private List<TntRdDetailAttachEx> rdDetailAttachList;

    /**
     * Get the latestRunEndDate.
     *
     * @return latestRunEndDate
     */
    public Date getLatestRunEndDate() {
        return this.latestRunEndDate;
    }

    /**
     * Set the latestRunEndDate.
     *
     * @param latestRunEndDate latestRunEndDate
     */
    public void setLatestRunEndDate(Date latestRunEndDate) {
        this.latestRunEndDate = latestRunEndDate;
        
    }

    /**
     * Get the shareList.
     *
     * @return shareList
     */
    public List<TntRdAttachCfc> getShareList() {
        return this.shareList;
    }

    /**
     * Set the shareList.
     *
     * @param shareList shareList
     */
    public void setShareList(List<TntRdAttachCfc> shareList) {
        this.shareList = shareList;
        
    }

    /**
     * Get the notInRundownList.
     *
     * @return notInRundownList
     */
    public List<TntNotInRundownEx> getNotInRundownList() {
        return this.notInRundownList;
    }

    /**
     * Set the notInRundownList.
     *
     * @param notInRundownList notInRundownList
     */
    public void setNotInRundownList(List<TntNotInRundownEx> notInRundownList) {
        this.notInRundownList = notInRundownList;
        
    }

    /**
     * Get the rdDetailAttachList.
     *
     * @return rdDetailAttachList
     */
    public List<TntRdDetailAttachEx> getRdDetailAttachList() {
        return this.rdDetailAttachList;
    }

    /**
     * Set the rdDetailAttachList.
     *
     * @param rdDetailAttachList rdDetailAttachList
     */
    public void setRdDetailAttachList(List<TntRdDetailAttachEx> rdDetailAttachList) {
        this.rdDetailAttachList = rdDetailAttachList;
        
    }

    /**
     * Get the stockDate.
     *
     * @return stockDate
     */
    public Date getStockDate() {
        return this.stockDate;
    }

    /**
     * Set the stockDate.
     *
     * @param stockDate stockDate
     */
    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
        
    }

}
