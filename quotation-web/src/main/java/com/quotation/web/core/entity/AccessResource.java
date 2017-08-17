package com.quotation.web.core.entity;

/**
 * Created by yang_shoulai on 8/16/2017.
 */
public class AccessResource {

    private String resourceCode;

    private String resourceDescKey;

    private String type;

    private String titleKey;

    private String menuUrl;

    private Integer menuIndex;

    private String menuLevel;

    private String menuTarget;

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceDescKey() {
        return resourceDescKey;
    }

    public void setResourceDescKey(String resourceDescKey) {
        this.resourceDescKey = resourceDescKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }

    public String getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuTarget() {
        return menuTarget;
    }

    public void setMenuTarget(String menuTarget) {
        this.menuTarget = menuTarget;
    }
}
