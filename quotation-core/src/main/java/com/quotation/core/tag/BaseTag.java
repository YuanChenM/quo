/**
 * @screen core
 * @author ma_b
 */
package com.quotation.core.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * The base class of tag lib.
 * 
 * @author ma_b
 * 
 */
public abstract class BaseTag extends SimpleTagSupport {

    /** logger */
    private static Logger logger = LoggerFactory.getLogger(BaseTag.class);

    /**
     * make the tag HTML.
     * 
     * @param request the current request
     * @throws JspException Subclasses can throw JspException to indicate an error occurred while processing this tag.
     * @throws IOException Subclasses can throw IOException if there was an error writing to the output stream
     * 
     */
    protected abstract void doTag(HttpServletRequest request) throws JspException, IOException;

    /**
     * 
     * <p>
     * make the tag HTML.
     * </p>
     * 
     * @throws JspException Subclasses can throw JspException to indicate an error occurred while processing this tag.
     * @throws IOException Subclasses can throw IOException if there was an error writing to the output stream
     * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
     * 
     * @author ma_b
     */
    public final void doTag() throws JspException, IOException {
        logger.debug("Tag {} start...", this.getClass().getName());

        this.doTag(this.getRequest());

        logger.debug("Tag {} end...", this.getClass().getName());
    }

    /**
     * get bean form application context
     * 
     * @param <T> the bean class type
     * @param name the bean name
     * @param requiredType the bean class type
     * @return the bean
     * @throws BeansException if the bean could not be created
     */
    protected <T> T getContextBean(String name, Class<T> requiredType) throws BeansException {

        T bean = null;

        PageContext pageContext = (PageContext) this.getJspContext();
        WebApplicationContext wac = WebApplicationContextUtils
            .getWebApplicationContext(pageContext.getServletContext());
        bean = (T) wac.getBean(name, requiredType);

        return bean;
    }

    /**
     * get request form application context
     * 
     * @return the current request
     */
    protected HttpServletRequest getRequest() {

        PageContext pageContext = (PageContext) this.getJspContext();
        return (HttpServletRequest) pageContext.getRequest();
    }
}
