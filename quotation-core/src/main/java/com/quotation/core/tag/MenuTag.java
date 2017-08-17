/**
 * @screen core
 * @author ma_b
 */
package com.quotation.core.tag;

import com.quotation.common.util.ApplicationContextHolder;
import com.quotation.core.consts.StringConst;
import com.quotation.core.util.StringUtil;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The menu tree's taglib
 *
 * @author ma_b
 */
public class MenuTag extends BaseTag {

    /**
     * level step
     */
    private static final int LEVEL_STEP = 2;

    /**
     * HTML for no URL
     */
    private static final String HTML_NOURL = "<a title=\"{0}\">{1}</a>";
    /**
     * HTML for URL
     */
    private static final String HTML_URL = "<a href=\"{0}\" target=\"{1}\" ref=\"main\" onclick=\"javascript:initTitleKey(''{2}'');\" title=\"{3}\">{4}</a>";

    /**
     * the style class
     */
    private String styleClass = "";

    /**
     * the context path
     */
    private String contextPath;

    /**
     * make the menu tree by access resources.
     *
     * @param request the current request
     * @throws JspException Subclasses can throw JspException to indicate an error occurred while processing this tag.
     * @throws IOException  Subclasses can throw IOException if there was an error writing to the output stream
     */
    public void doTag(HttpServletRequest request) throws JspException, IOException {
        this.contextPath = request.getContextPath();
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        Locale locale = null;
        if (localeResolver == null || (locale = localeResolver.resolveLocale(request)) == null) {
            locale = Locale.getDefault();
        }
        MessageSource messageSource = ApplicationContextHolder.getBean(MessageSource.class);
        //TODO MenuTag Test
        List<MenuItem> items = new ArrayList<>();

        MenuItem item = new MenuItem();
        item.setCode("001");
        item.setLevel("l1");
        item.setUrl("sp/list");
        item.setTarget("navTab");
        item.setTitle("Sample List");
        item.setTooltip("Sample List");
        items.add(item);

        item = new MenuItem();
        item.setCode("002");
        item.setLevel("l2");
        item.setTitle("Test List");
        item.setTooltip("Test List");
        items.add(item);

        item = new MenuItem();
        item.setCode("003");
        item.setLevel("l201#01");
        item.setUrl("/sp/test/list1");
        item.setTarget("navTab");
        item.setTitle("List1");
        item.setTooltip("List1");
        items.add(item);

        item = new MenuItem();
        item.setCode("004");
        item.setLevel("l202#02");
        item.setUrl("/sp/test/list2");
        item.setTarget("navTab");
        item.setTitle("List2");
        item.setTooltip("List2");
        items.add(item);

        item = new MenuItem();
        item.setCode("005");
        item.setLevel("l203#03");
        item.setUrl("/sp/test/list3");
        item.setTarget("navTab");
        item.setTitle("List3");
        item.setTooltip("List3");
        items.add(item);

        item = new MenuItem();
        item.setCode("007");
        item.setLevel("l4");
        item.setUrl("");
        item.setTarget("navTab");
        item.setTitle("Export");
        item.setTooltip(item.getTitle());
        items.add(item);

        item = new MenuItem();
        item.setCode("008");
        item.setLevel("l401#01");
        item.setUrl("");
        item.setTarget("navTab");
        item.setTitle("Cost Study Quotation");
        item.setTooltip(item.getTitle());
        items.add(item);

        item = new MenuItem();
        item.setCode("009");
        item.setLevel("l40101#01");
        item.setUrl("");
        item.setTarget("navTab");
        item.setTitle("Quotation Summary List");
        item.setTooltip(item.getTitle());
        items.add(item);

        item = new MenuItem();
        item.setCode("010");
        item.setLevel("l5");
        item.setTitle(messageSource.getMessage("Master_Label", null, locale));
        item.setTooltip(item.getTitle());
        items.add(item);

        item = new MenuItem();
        item.setCode("011");
        item.setLevel("l501#01");
        item.setUrl("/QSM0101");
        item.setTarget("navTab");
        item.setTitle(messageSource.getMessage("QSM0101_Label_Menu", null, locale));
        item.setTooltip(item.getTitle());
        items.add(item);

        item = new MenuItem();
        item.setCode("012");
        item.setLevel("l6");
        item.setUrl("");
        item.setTarget("navTab");
        item.setTitle("Information");
        item.setTooltip(item.getTitle());
        items.add(item);

        item = new MenuItem();
        item.setCode("013");
        item.setLevel("l601#01");
        item.setUrl("/QSC0301");
        item.setTarget("navTab");
        item.setTitle("Information List");
        item.setTooltip(item.getTitle());
        items.add(item);

        item = new MenuItem();
        item.setCode("014");
        item.setLevel("l7");
        item.setUrl("/grid");
        item.setTarget("navTab");
        item.setTitle("Grid");
        item.setTooltip(item.getTitle());
        items.add(item);
        getJspContext().getOut().write(this.makeMenuHtml(items));
    }

    /**
     * make the menu tree html
     *
     * @param items menu items
     * @return the menu tree html
     */
    private String makeMenuHtml(List<MenuItem> items) {
        StringBuilder out = new StringBuilder();
        if (items != null) {
            String prevLevel = "";
            String parentLevel = "";

            // root node start
            out.append("<ul class=\"").append(this.styleClass).append("\">");
            for (MenuItem menuItem : items) {

                String level = StringUtil.toSafeString(menuItem.getLevel());
                if (level.contains(StringConst.Hash_Key)) {
                    level = level.substring(0, level.indexOf(StringConst.Hash_Key));
                }
                parentLevel = this.mergeLevel(prevLevel, level);

                // node's end tag
                if (parentLevel.length() < prevLevel.length()) {
                    for (int i = prevLevel.length(); i > parentLevel.length(); i -= LEVEL_STEP) {
                        out.append("</li>");
                        if (i > level.length()) {
                            out.append("\r\n</ul>");
                        }
                    }
                }

                // node's start tag
                if (level.length() > parentLevel.length()) {
                    for (int i = parentLevel.length(); i < level.length(); i += LEVEL_STEP) {
                        if (prevLevel.length() > 0 && i >= prevLevel.length()) {
                            out.append("\r\n<ul>");
                            if (i < (level.length() - LEVEL_STEP)) {
                                out.append("\r\n<li>");
                            }
                        }
                    }
                }

                out.append("\r\n<li>");
                // menu node
                out.append(this.makeOneMenu(menuItem));

                prevLevel = level;
            }
            // node's end tag for last node
            for (int i = prevLevel.length(); i > 0; i -= LEVEL_STEP) {
                out.append("</li>");
                if (i > 1) {
                    out.append("\r\n</ul>");
                }
            }

            // root node end
            out.append("\r\n</ul>");
        }

        return out.toString();
    }

    /**
     * the same level of the previous node and the current node
     *
     * @param prev the previous node's level
     * @param curr the current node's level
     * @return the same level
     */
    private String mergeLevel(String prev, String curr) {
        String sameLevel = "";

        int prevLen = prev.length();
        int currLen = curr.length();
        int minLen = Math.min(prevLen, currLen);
        for (int i = LEVEL_STEP; i <= minLen; i += LEVEL_STEP) {
            int pos = i;
            if (prev.substring(0, pos).equals(curr.substring(0, pos))) {
                sameLevel = prev.substring(0, pos);
            } else {
                break;
            }
        }

        return sameLevel;
    }

    /**
     * make one menu's HTML
     *
     * @return one menu's HTML
     */
    private String makeOneMenu(MenuItem item) {
        StringBuilder menu = new StringBuilder();

        String url = makeUrl(item.getUrl());
        String target = item.getTarget();
        String title = StringUtil.escapeHtml(item.getTitle());
        String resourceCode = item.getCode();
        String tooltip = StringUtil.escapeHtml(item.getTooltip());

        if (StringUtil.isEmpty(url) || "#".equals(url)) {
            menu.append(StringUtil.formatMessage(HTML_NOURL, tooltip, title));

        } else {
            menu.append(StringUtil.formatMessage(HTML_URL, url, target, resourceCode, tooltip, title));

        }

        return menu.toString();
    }

    /**
     * <p>
     * Make the menu URL, plus the context path in the front of the URL.
     * </p>
     *
     * @param menuUrl the URL in data base
     * @return the URL with context path
     * @author ma_b
     */
    private String makeUrl(String menuUrl) {
        String url = menuUrl;

        if (!StringUtil.isEmpty(url) && !"#".equals(url) && !StringUtil.isEmpty(this.contextPath)) {
            if (url.startsWith("/")) {
                url = this.contextPath.concat(url);
            } else {
                url = this.contextPath.concat("/").concat(url);
            }
        }

        return url;
    }

    /**
     * <p>
     * get the styleClass.
     * </p>
     *
     * @return styleClass
     * @author ma_b
     */
    public String getStyleClass() {
        return this.styleClass;
    }

    /**
     * <p>
     * set the styleClass.
     * </p>
     *
     * @param styleClass styleClass
     * @author ma_b
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}
