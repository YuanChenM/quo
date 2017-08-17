package com.quotation.web.sample.entity;

import com.quotation.core.base.BaseEntity;
import com.quotation.core.bean.BaseItem;

import java.util.List;

/**
 * Created by zhu_yingjie on 2017/8/15.
 */
public class GridEntity extends BaseItem {
    private String no;
    private String date;
    private String remark;
    private String sellingTo;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSellingTo() {
        return sellingTo;
    }

    public void setSellingTo(String sellingTo) {
        this.sellingTo = sellingTo;
    }

}
