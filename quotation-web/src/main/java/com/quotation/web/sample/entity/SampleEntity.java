package com.quotation.web.sample.entity;

import com.quotation.core.base.BaseEntity;

import java.sql.Timestamp;

/**
 * Created by yuan_chen1 on 2017/7/19.
 *
 * SampleEntity
 */
public class SampleEntity extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String sampleUid;
    private int sampleId;
    private String sampleCode;
    private String sampleContent;
    private Integer createdBy;
    private Timestamp createdDate;
    private Integer updatedBy;
    private Timestamp updatedDate;
    private Integer version;

    /**
     * Getter method for property <tt>sampleUid</tt>.
     *
     * @return property value of sampleUid
     */
    public String getSampleUid() {
        return sampleUid;
    }

    /**
     * Setter method for property <tt>sampleUid</tt>.
     *
     * @param sampleUid value to be assigned to property sampleUid
     */
    public void setSampleUid(String sampleUid) {
        this.sampleUid = sampleUid;
    }

    /**
     * Getter method for property <tt>sampleId</tt>.
     *
     * @return property value of sampleId
     */
    public int getSampleId() {
        return sampleId;
    }

    /**
     * Setter method for property <tt>sampleId</tt>.
     *
     * @param sampleId value to be assigned to property sampleId
     */
    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    /**
     * Getter method for property <tt>sampleCode</tt>.
     *
     * @return property value of sampleCode
     */
    public String getSampleCode() {
        return sampleCode;
    }

    /**
     * Setter method for property <tt>sampleCode</tt>.
     *
     * @param sampleCode value to be assigned to property sampleCode
     */
    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    /**
     * Getter method for property <tt>sampleContent</tt>.
     *
     * @return property value of sampleContent
     */
    public String getSampleContent() {
        return sampleContent;
    }

    /**
     * Setter method for property <tt>sampleContent</tt>.
     *
     * @param sampleContent value to be assigned to property sampleContent
     */
    public void setSampleContent(String sampleContent) {
        this.sampleContent = sampleContent;
    }

    /**
     * Getter method for property <tt>createdBy</tt>.
     *
     * @return property value of createdBy
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter method for property <tt>createdBy</tt>.
     *
     * @param createdBy value to be assigned to property createdBy
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter method for property <tt>createdDate</tt>.
     *
     * @return property value of createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter method for property <tt>createdDate</tt>.
     *
     * @param createdDate value to be assigned to property createdDate
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Getter method for property <tt>updatedBy</tt>.
     *
     * @return property value of updatedBy
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Setter method for property <tt>updatedBy</tt>.
     *
     * @param updatedBy value to be assigned to property updatedBy
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Getter method for property <tt>updatedDate</tt>.
     *
     * @return property value of updatedDate
     */
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Setter method for property <tt>updatedDate</tt>.
     *
     * @param updatedDate value to be assigned to property updatedDate
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Getter method for property <tt>version</tt>.
     *
     * @return property value of version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Setter method for property <tt>version</tt>.
     *
     * @param version value to be assigned to property version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
