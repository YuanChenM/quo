package com.quotation.core.tag;

import com.quotation.core.base.BaseEntity;

import java.sql.Timestamp;

/**
 * Created by yuan_chen1 on 2017/7/27.
 *
 * SampleEntity
 */
public class MenuItem extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String code;
    private String url;
    private String target;
    private String title;
    private String tooltip;
    private String level;

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>url</tt>.
     *
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for property <tt>url</tt>.
     *
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter method for property <tt>target</tt>.
     *
     * @return property value of target
     */
    public String getTarget() {
        return target;
    }

    /**
     * Setter method for property <tt>target</tt>.
     *
     * @param target value to be assigned to property target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Getter method for property <tt>title</tt>.
     *
     * @return property value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for property <tt>title</tt>.
     *
     * @param title value to be assigned to property title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for property <tt>tooltip</tt>.
     *
     * @return property value of tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Setter method for property <tt>tooltip</tt>.
     *
     * @param tooltip value to be assigned to property tooltip
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Getter method for property <tt>level</tt>.
     *
     * @return property value of level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Setter method for property <tt>level</tt>.
     *
     * @param level value to be assigned to property level
     */
    public void setLevel(String level) {
        this.level = level;
    }
}
