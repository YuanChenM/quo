package com.quotation.web.core.bean;

import com.quotation.web.core.entity.Role;

import java.util.List;

/**
 * Created by yang_shoulai on 8/15/2017.
 */
public class RoleInfo extends Role{

    private String OfficeCode;

    private String sectionCode;

    private List<String> resourceCodes;

    public String getOfficeCode() {
        return OfficeCode;
    }

    public void setOfficeCode(String officeCode) {
        OfficeCode = officeCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public List<String> getResourceCodes() {
        return resourceCodes;
    }

    public void setResourceCodes(List<String> resourceCodes) {
        this.resourceCodes = resourceCodes;
    }
}
