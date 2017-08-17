package com.quotation.core.bean;

import java.util.List;

/**
 * Created by zhu_yingjie on 2017/8/16.
 */
public class BaseItem {
    private List<? extends BaseItem> subList;

    public List<? extends BaseItem> getSubList() {
        return subList;
    }

    public void setSubList(List<? extends BaseItem> subList) {
        this.subList = subList;
    }
}
