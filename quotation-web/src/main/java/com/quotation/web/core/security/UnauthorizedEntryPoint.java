package com.quotation.web.core.security;

import com.quotation.web.core.constant.MessageCode;
import com.quotation.web.core.control.BaseResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yang_shoulai on 8/14/2017.
 */

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final MessageSource messageSource;

    public UnauthorizedEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        if (request.getHeader("X-Requested-With") != null) {
            response.setStatus(HttpStatus.OK.value());
            BaseResponse baseResponse = new BaseResponse(BaseResponse.STATUS_TIMEOUT, BaseResponse.STATUS_TIMEOUT,
                    messageSource.getMessage(MessageCode.System.SESSION_TIMEOUT, null, LocaleContextHolder.getLocale()));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(baseResponse.toJson());
            response.getWriter().flush();
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
