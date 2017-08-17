package com.quotation.web.core.entity;

import java.util.Date;

/**
 * Created by yang_shoulai on 8/14/2017.
 */
public class User {

    private Integer userId;

    private String loginId;

    private String username;

    private String pwd;

    private String mailAddr;

    private String companyCode;

    private Integer status;

    private Integer missCount;

    private Date lastNgTime;

    private Date expiryDate;

    private Date lastLoginTime;

    private String defaultOffice;

    private String defaultSection;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Integer version;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMailAddr() {
        return mailAddr;
    }

    public void setMailAddr(String mailAddr) {
        this.mailAddr = mailAddr;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMissCount() {
        return missCount;
    }

    public void setMissCount(Integer missCount) {
        this.missCount = missCount;
    }

    public Date getLastNgTime() {
        return lastNgTime;
    }

    public void setLastNgTime(Date lastNgTime) {
        this.lastNgTime = lastNgTime;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getDefaultOffice() {
        return defaultOffice;
    }

    public void setDefaultOffice(String defaultOffice) {
        this.defaultOffice = defaultOffice;
    }

    public String getDefaultSection() {
        return defaultSection;
    }

    public void setDefaultSection(String defaultSection) {
        this.defaultSection = defaultSection;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
