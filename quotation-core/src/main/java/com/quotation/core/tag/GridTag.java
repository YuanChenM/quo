package com.quotation.core.tag;

import com.quotation.core.bean.BaseItem;
import com.quotation.core.util.BeanUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhu_yingjie on 2017/8/16.
 */
public class GridTag extends SimpleTagSupport {
    private List<? extends BaseItem> items;

    private List<Column> columns;

    private boolean group;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public List<? extends BaseItem> getItems() {
        return items;
    }

    public void setItems(List<? extends BaseItem> items) {
        this.items = items;
    }

    @Override public void doTag() throws JspException, IOException {
        super.doTag();
        int columnLen = columns.size();
        String idProp = columns.get(0).getProp();
        JspWriter out = this.getJspContext().getOut();
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"list\">\n");
        sb.append("<thead>\n");
        sb.append("<th width=\"50\"><input type=\"checkbox\" class=\"checkboxCtrl\" group=\"grid\"/></th>\n");
        if (group) {
            sb.append("<th width=\"50\"></th>\n");
        }
        for (int i = 0; i < columnLen; i++) {
            Column column = columns.get(i);
            sb.append("<th");
            if (column.getWidth() != null) {
                sb.append(" width=\"" + column.getWidth() + "\">");
            } else {
                sb.append(">");
            }
            sb.append(column.getLabel() + "</th>\n");
        }
        sb.append("</thead>\n");
        sb.append("<tbody>\n");
        for (BaseItem item : items) {
            sb.append("<tr class=\"parent\" id=\"" + BeanUtil.getFieldValueByName(idProp, item) + "\">\n");
            sb.append("<td><input type=\"checkbox\" name=\"grid\" value=\"" + BeanUtil.getFieldValueByName(idProp, item)
                    + "\"/></td>\n");
            if (group) {
                if (item.getSubList() != null && item.getSubList().size() > 0) {
                    sb.append("<td onclick=\"toggleTr(this)\">+</td>\n");
                } else {
                    sb.append("<td></td>");
                }
            }
            setValueForTD(item, sb, columnLen);
            if (group) {
                if (item.getSubList() != null && item.getSubList().size() > 0) {
                    for (BaseItem subItem : item.getSubList()) {
                        sb.append("<tr class=\"children_" + BeanUtil.getFieldValueByName(idProp, item)
                                + "\" style=\"display: none\">\n");
                        sb.append("<td><input type=\"checkbox\" name=\"grid\" value=\"" + BeanUtil
                                .getFieldValueByName(idProp, subItem) + "\"/></td>\n");
                        sb.append("<td></td>\n");
                        setValueForTD(subItem, sb, columnLen);
                    }
                }
            }
        }
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        out.println(sb);
    }

    private void setValueForTD(BaseItem item, StringBuffer sb, int columnLen) {
        for (int i = 0; i < columnLen; i++) {
            sb.append("<td>");
            if (columns.get(i).getUrl() != null) {
                sb.append("<a href=\""+ columns.get(i).getUrl() + BeanUtil.getFieldValueByName(columns.get(i).getProp(), item) +"\">");
            }
            sb.append(BeanUtil.getFieldValueByName(columns.get(i).getProp(), item));
            if (columns.get(i).getUrl() != null) {
                sb.append("</a>");
            }
            sb.append("</td>\n");
        }
    }
}
