package com.quotation.web.common.condition;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by zhu_yingjie on 2017/8/9.
 */
public class QSC0301Condition {

    private String title;

    private String information;

    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    private Date dateFromStart;

    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    private Date dateFromEnd;

    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    private Date dateToStart;

    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    private Date dateToEnd;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getDateFromEnd() {
        return dateFromEnd;
    }

    public void setDateFromEnd(Date dateFromEnd) {
        this.dateFromEnd = dateFromEnd;
    }

    public Date getDateFromStart() {
        return dateFromStart;
    }

    public void setDateFromStart(Date dateFromStart) {
        this.dateFromStart = dateFromStart;
    }

    public Date getDateToEnd() {
        return dateToEnd;
    }

    public void setDateToEnd(Date dateToEnd) {
        this.dateToEnd = dateToEnd;
    }

    public Date getDateToStart() {
        return dateToStart;
    }

    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }
}
