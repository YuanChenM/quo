package com.quotation.web.common.entity;

import com.quotation.core.base.BaseEntity;

import java.util.Date;

/**
 * Created by zhu_yingjie on 2017/8/8.
 */
public class QSC0303Entity extends BaseEntity{

    private static final long serialVersionUID = 8666640822133544127L;

    private Integer informationId;

    private String title;

    private String information;

    private String userName;

    private Date updatedDate;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getInformationId() {
        return informationId;
    }

    public void setInformationId(Integer informationId) {
        this.informationId = informationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
