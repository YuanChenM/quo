/**
 * Login User info.
 * 
 * @screen common
 * 
 */
package com.quotation.common.bean;

import com.quotation.core.base.BaseEntity;

/**
 * login user info
 * 
 * 
 * 
 */
public class UserAccessInfo extends BaseEntity {

    /** serialVersionUID */
    private static final long serialVersionUID = 7261512169282313058L;

    /** The rootId. */
    private Integer officeId;

    /** The rootId. */
    private Integer sysRootId;

    /** The rootId. */
    private String rootId;

    /** The resourceId. */
    private Integer sysResourceId;

    /** The resourceId. */
    private String resourceId;

    /** The resourceUrl. */
    private String resourceUrl;

    /** The timezone. */
    private String timezone;

    /**
     * office Code
     */
    private String officeCode;

    /**
     * region Code
     */
    private String regionCode;

    /**
     * access level
     */
    private Integer accessLevel;

    /** The inactiveFlag. */
    private Integer inactiveFlag;

    /**
     * Get the accessLevel.
     * 
     * @return accessLevel
     * 
     * 
     */
    public Integer getAccessLevel() {
        return this.accessLevel;
    }

    /**
     * Set the accessLevel.
     * 
     * @param accessLevel accessLevel
     * 
     * 
     */
    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;

    }

    /**
     * Get the officeCode.
     * 
     * @return officeCode
     * 
     * 
     */
    public String getOfficeCode() {
        return this.officeCode;
    }

    /**
     * Set the officeCode.
     * 
     * @param officeCode officeCode
     * 
     * 
     */
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;

    }

    /**
     * Get the regionCode.
     * 
     * @return regionCode
     * 
     * 
     */
    public String getRegionCode() {
        return this.regionCode;
    }

    /**
     * Set the regionCode.
     * 
     * @param regionCode regionCode
     * 
     * 
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;

    }

    /**
     * Get the resourceId.
     * 
     * @return resourceId
     * 
     * 
     */
    public String getResourceId() {
        return this.resourceId;
    }

    /**
     * Set the resourceId.
     * 
     * @param resourceId resourceId
     * 
     * 
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;

    }

    /**
     * Get the rootId.
     * 
     * @return rootId
     * 
     * 
     */
    public String getRootId() {
        return this.rootId;
    }

    /**
     * Set the rootId.
     * 
     * @param rootId rootId
     * 
     * 
     */
    public void setRootId(String rootId) {
        this.rootId = rootId;

    }

    /**
     * Get the resourceUrl.
     * 
     * @return resourceUrl
     * 
     * 
     */
    public String getResourceUrl() {
        return this.resourceUrl;
    }

    /**
     * Set the resourceUrl.
     * 
     * @param resourceUrl resourceUrl
     * 
     * 
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    /**
     * Get the officeId.
     *
     * @return officeId
     */
    public Integer getOfficeId() {
        return this.officeId;
    }

    /**
     * Set the officeId.
     *
     * @param officeId officeId
     */
    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
        
    }

    /**
     * Get the timezone.
     *
     * @return timezone
     */
    public String getTimezone() {
        return this.timezone;
    }

    /**
     * Set the timezone.
     *
     * @param timezone timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
        
    }

    /**
     * Get the sysResourceId.
     *
     * @return sysResourceId
     */
    public Integer getSysResourceId() {
        return this.sysResourceId;
    }

    /**
     * Set the sysResourceId.
     *
     * @param sysResourceId sysResourceId
     */
    public void setSysResourceId(Integer sysResourceId) {
        this.sysResourceId = sysResourceId;
        
    }

    /**
     * Get the sysRootId.
     *
     * @return sysRootId
     */
    public Integer getSysRootId() {
        return this.sysRootId;
    }

    /**
     * Set the sysRootId.
     *
     * @param sysRootId sysRootId
     */
    public void setSysRootId(Integer sysRootId) {
        this.sysRootId = sysRootId;
        
    }

    /**
     * Get the inactiveFlag.
     *
     * @return inactiveFlag
     */
    public Integer getInactiveFlag() {
        return this.inactiveFlag;
    }

    /**
     * Set the inactiveFlag.
     *
     * @param inactiveFlag inactiveFlag
     */
    public void setInactiveFlag(Integer inactiveFlag) {
        this.inactiveFlag = inactiveFlag;
        
    }
}
