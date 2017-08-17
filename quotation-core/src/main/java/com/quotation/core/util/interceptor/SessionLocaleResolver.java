/**
 * SessionLocaleResolver.java
 * 
 * @screen core
 * 
 */
package com.quotation.core.util.interceptor;

import com.quotation.common.bean.UserInfo;
import com.quotation.common.util.SessionInfoManager;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * SessionLocaleResolver.
 */
public class SessionLocaleResolver extends org.springframework.web.servlet.i18n.SessionLocaleResolver {

    /**
     * Determine the default locale for the given request, Called if no locale session attribute has been found.
     * <p>
     * The default implementation returns the specified default locale, if any, else falls back to the request's
     * accept-header locale.
     * 
     * @param request the request to resolve the locale for
     * @return the default locale (never <code>null</code>)
     * @see #setDefaultLocale
     * @see javax.servlet.http.HttpServletRequest#getLocale()
     */
    protected Locale determineDefaultLocale(HttpServletRequest request) {
        Locale locale = null;

        SessionInfoManager context = SessionInfoManager.getContextInstance(request);
        UserInfo loginUser = context.getLoginInfo();
        if (loginUser != null && loginUser.getLanguage() != null) {
            // get default from user info
            locale = StringUtils.parseLocaleString(String.valueOf(loginUser.getLanguage()));
        }

        if (locale == null) {
            locale = super.determineDefaultLocale(request);
        }
        return locale;
    }
}
