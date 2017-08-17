package com.quotation.web.master.entity;

import com.quotation.core.base.BaseEntity;

import java.util.Date;

/**
 * Created by yang_shoulai on 8/8/2017.
 */
public class TnmSystemMasterEntity extends BaseEntity {

    private int masterId;

    private String masterName;

    private Date lastUploadDate;

    private int readOnly;

    private int createdBy;

    private Date createdDate;

    private int updatedBy;

    private Date updatedDate;

    private int version;

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Date getLastUploadDate() {
        return lastUploadDate;
    }

    public void setLastUploadDate(Date lastUploadDate) {
        this.lastUploadDate = lastUploadDate;
    }

    public int getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(int readOnly) {
        this.readOnly = readOnly;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
