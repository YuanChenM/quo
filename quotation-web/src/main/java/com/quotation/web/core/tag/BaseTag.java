package com.quotation.web.core.tag;

import com.quotation.common.util.ApplicationContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Created by yang_shoulai on 8/16/2017.
 */
public class BaseTag extends SimpleTagSupport {


    protected String resolveMessage(String msgCode, Object... args) {
        return getBean(MessageSource.class).getMessage(msgCode, args, LocaleContextHolder.getLocale());
    }

    protected <T> T getBean(Class<T> clz) {
        return ApplicationContextHolder.getBean(clz);
    }

    protected HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
    }
}
