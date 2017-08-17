package com.quotation.core.tag;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.quotation.common.util.MessageManager;
import com.quotation.core.util.StringUtil;

/**
 * @author li_hui
 * 
 */
public class PageTag extends SimpleTagSupport {
    private Page<?> page;
    private String action;
    private String parentFormId;
    private String targetType;

    /**
     * Get the page.
     * 
     * @return page
     * 
     */
    public Page<?> getPage() {
        return this.page;
    }

    /**
     * Set the page.
     * 
     * @param page page
     * 
     */
    public void setPage(Page<?> page) {
        this.page = page;
    }

    /**
     * Get the action.
     * 
     * @return action
     * 
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Set the action.
     * 
     * @param action action
     * 
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Get the parentFormId.
     * 
     * @return parentFormId
     * 
     */
    public String getParentFormId() {
        return this.parentFormId;
    }

    /**
     * Set the parentFormId.
     * 
     * @param parentFormId parentFormId
     * 
     */
    public void setParentFormId(String parentFormId) {
        this.parentFormId = parentFormId;
    }

    /**
     * Get the targetType.
     * 
     * @return targetType
     * 
     */
    public String getTargetType() {
        return this.targetType;
    }

    /**
     * Set the targetType.
     * 
     * @param targetType targetType
     * 
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @Override
    public void doTag() throws JspException, IOException {
        super.doTag();
        int currentPage = page.getNumber() + 1;
        int pageSize = page.getSize();
        long totalCount = page.getTotalElements();
        // int pageCount=page.getTotalPages();
        String orderColumn = "";
        String desc = "";
        Sort sort = page.getSort();
        if (sort != null) {
            Iterator<Order> it = sort.iterator();
            Order order = null;
            if (it.hasNext()) {
                order = it.next();
                orderColumn = order.getProperty();
                desc = order.getDirection().toString().toLowerCase();
            }
        }

        int pageNumberStart = pageSize * (currentPage - 1) + 1;
        // int pageNumberLimit=page.getNumberOfElements();
        int pageNumberLimit = pageSize * (currentPage - 1) + page.getNumberOfElements();
        if (pageNumberLimit == 0) {
            pageNumberStart = 0;
        }
        JspWriter out = this.getJspContext().getOut();
        StringBuffer sb = new StringBuffer();
        // form
        sb.append("<form id=\"pagerForm\" action=\"" + action + "\" method=\"post\">\n");
        sb.append("<input type=\"hidden\" name=\"page.page\" value=\"" + currentPage + "\" />\n");
        sb.append("<input type=\"hidden\" name=\"page.size\" value=\"" + pageSize + "\"/>\n");
        sb.append("<input type=\"hidden\" name=\"page.sort\" value=\"" + orderColumn + "\"/>\n");
        sb.append("<input type=\"hidden\" name=\"page.sort.dir\" value=\"" + desc + "\" />\n");
        sb.append("</form>\n");
        // script
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("$(\"#" + parentFormId + "\").attr(\"rel\",\"pagerForm\");\n");
        sb.append("</script>\n");
        // page bar
        sb.append("<div class=\"panelBar\">\n");
        sb.append("<div class=\"pages\">\n");
        sb.append("<span>&nbsp;&nbsp;" + MessageManager.getMessage("Core_Label_Record") + "&nbsp;"
                + pageNumberStart + "-" + pageNumberLimit + "&nbsp;of&nbsp;" + totalCount + "</span>\n");
        sb.append("</div>\n");
        int tp = 1;
        if (page.getTotalPages() > 0) {
            tp = page.getTotalPages();
        }
        String targetType = "navTab";
        if (!StringUtil.isEmpty(this.targetType)) {
            targetType = this.getTargetType();
        }
        sb.append("<div class=\"pagination\" targetType=\"" + targetType + "\" totalCount=\"" + totalCount
                + "\" numPerPage=\"" + pageSize + "\" pageNumShown=\"0\" totalPages=\"" + tp + "\" currentPage=\""
                + currentPage + "\"/>\n");
        sb.append("</div>\n");

        out.println(sb);
    }
}
