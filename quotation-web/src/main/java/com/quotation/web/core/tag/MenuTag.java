package com.quotation.web.core.tag;

import com.quotation.core.util.StringUtil;
import com.quotation.web.core.entity.AccessResource;
import com.quotation.web.core.security.UserContext;
import com.quotation.web.core.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.List;

/**
 * Created by yang_shoulai on 8/16/2017.
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
    private static final String HTML_URL = "<a href=\"{0}\" target=\"{1}\" rel=\"{5}\" onclick=\"javascript:initTitleKey(''{2}'');\" title=\"{3}\">{4}</a>";

    private static final String AR_LEVEL_PLACE_HOLDRE = "#";

    private String styleClass = "";

    @Override
    public void doTag() throws JspException, IOException {
        UserService userService = this.getBean(UserService.class);
        UserContext userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AccessResource> accessResources = userService.findAccessResource(userContext.getLoginId(), userContext.getActiveOffice(), userContext.getActiveSection());
        this.getJspContext().getOut().write(makeMenuHtml(accessResources));
    }

    private String makeMenuHtml(List<AccessResource> resources) {
        StringBuilder out = new StringBuilder();
        if (resources != null) {
            String prevLevel = "";
            String parentLevel = "";
            // root node start
            out.append("<ul class=\"").append(styleClass).append("\">");
            for (AccessResource resource : resources) {
                String level = StringUtil.toSafeString(resource.getMenuLevel());
                if (level.contains(AR_LEVEL_PLACE_HOLDRE)) {
                    level = level.substring(0, level.indexOf(AR_LEVEL_PLACE_HOLDRE));
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
                            out.append("\r\n").append("<ul>");
                            if (i < (level.length() - LEVEL_STEP)) {
                                out.append("\r\n").append("<li>");
                            }
                        }
                    }
                }

                out.append("\r\n").append("<li>");
                // menu node
                out.append(this.makeOneMenu(resource));
                prevLevel = level;
            }
            // node's end tag for last node
            for (int i = prevLevel.length(); i > 0; i -= LEVEL_STEP) {
                out.append("</li>");
                if (i > 1) {
                    out.append("\r\n").append("</ul>");
                }
            }
            // root node end
            out.append("\r\n").append("</ul>");
        }

        return out.toString();
    }

    private String mergeLevel(String prev, String curr) {
        String sameLevel = "";
        int prevLen = prev.length();
        int currLen = curr.length();
        for (int i = LEVEL_STEP; i <= Math.min(prevLen, currLen); i += LEVEL_STEP) {
            if (prev.substring(0, i).equals(curr.substring(0, i))) {
                sameLevel = prev.substring(0, i);
            } else {
                break;
            }
        }
        return sameLevel;
    }

    private String makeOneMenu(AccessResource resource) {
        StringBuffer menu = new StringBuffer();
        String url = makeUrl(resource.getMenuUrl());
        String target = resource.getMenuTarget();
        String title = StringUtil.escapeHtml(resolveMessage(resource.getResourceDescKey()));
        String resourceCode = resource.getResourceCode();
        String tooltip = StringUtil.escapeHtml(resolveMessage(resource.getTitleKey()));
        if (StringUtil.isEmpty(url) || "#".equals(url)) {
            menu.append(StringUtil.formatMessage(HTML_NOURL, tooltip, title));
        } else {
            menu.append(StringUtil.formatMessage(HTML_URL, url, target, resourceCode, tooltip, title, resourceCode));
        }
        return menu.toString();
    }

    private String makeUrl(String menuUrl) {
        String contextPath = getHttpRequest().getContextPath();
        if (!StringUtil.isEmpty(menuUrl) && !"#".equals(menuUrl) && !StringUtil.isEmpty(contextPath)) {
            menuUrl = menuUrl.startsWith("/") ? contextPath.concat(menuUrl) : contextPath.concat("/").concat(menuUrl);
        }
        return menuUrl;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}
