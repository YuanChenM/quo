package com.quotation.web.common.entity;

import com.quotation.core.base.BaseEntity;

import java.util.Date;

/**
 * Created by zhu_yingjie on 2017/8/8.
 */
public class QSC0301Entity extends BaseEntity{

    private static final long serialVersionUID = -7630839168979468415L;

    private Integer informationId;

    private String title;

    private String information;

    private Date dateFrom;

    private Date dateTo;

    public Integer getInformationId() {
        return informationId;
    }

    public void setInformationId(Integer informationId) {
        this.informationId = informationId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
