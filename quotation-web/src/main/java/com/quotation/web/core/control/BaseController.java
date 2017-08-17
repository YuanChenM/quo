package com.quotation.web.core.control;

import com.quotation.web.core.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by yang_shoulai on 8/14/2017.
 */
public abstract class BaseController {

    @Autowired
    private MessageSource messageSource;

    /**
     * get current sign in user
     *
     * @return current user info, null if user not login in
     */
    public UserContext getUserContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) return null;
        UserContext context = (UserContext) authentication.getPrincipal();
        return context;
    }

    /**
     * resolve i18n message
     *
     * @param msgCode the message code
     * @param args    the message args
     * @return the locale message related with given message code
     */
    public String resolveMessage(String msgCode, Object... args) {
        return this.messageSource.getMessage(msgCode, args, LocaleContextHolder.getLocale());
    }


    /**
     * response http request with error message
     *
     * @param msgCode the i18n message code
     * @param args    message arguments
     * @return error response
     */
    public BaseResponse error(String msgCode, Object... args) {
        BaseResponse response = new BaseResponse(BaseResponse.STATUS_ERROR);
        response.addMessage(resolveMessage(msgCode, args));
        return response;
    }

    /**
     * response http request with error message
     *
     * @return error response
     */
    public BaseResponse error() {
        return new BaseResponse(BaseResponse.STATUS_ERROR);
    }

    /**
     * response http request with success message
     *
     * @param msgCode the i18n message code
     * @param args    message arguments
     * @return error response
     */
    public BaseResponse success(String msgCode, Object... args) {
        BaseResponse response = new BaseResponse(BaseResponse.STATUS_SUCCESS);
        response.addMessage(resolveMessage(msgCode, args));
        return response;
    }

    /**
     * response http request with success message
     *
     * @return error response
     */
    public BaseResponse success() {
        return new BaseResponse(BaseResponse.STATUS_SUCCESS);
    }

    /**
     * response http request with timeout message
     *
     * @return error response
     */
    public BaseResponse timeout() {
        return new BaseResponse(BaseResponse.STATUS_TIMEOUT, BaseResponse.STATUS_TIMEOUT);
    }
}
