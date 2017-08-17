package com.quotation.core.tag;

/**
 * Created by zhu_yingjie on 2017/8/16.
 */
public class Column {
    //列名
    private String label;
    //列宽
    private String width;
    //列属性
    private String prop;
    //url
    private String url;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Column(String label, String prop, String url, String width) {
        this.label = label;
        this.prop = prop;
        this.url = url;
        this.width = width;
    }

    public Column () {}
}
