package com.quotation.web.core.exception;

import com.quotation.core.bean.BaseMessage;
import com.quotation.core.exception.BusinessException;
import com.quotation.core.exception.TimeoutException;
import com.quotation.web.core.constant.MessageCode;
import com.quotation.web.core.control.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang_shoulai on 8/14/2017.
 */
@ControllerAdvice
public class HandlerExceptionAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {TimeoutException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseResponse timeoutException(TimeoutException ex) {
        return new BaseResponse(BaseResponse.STATUS_TIMEOUT, BaseResponse.STATUS_TIMEOUT,
                messageSource.getMessage(MessageCode.System.SESSION_TIMEOUT, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseResponse businessException(BusinessException ex) {
        List<BaseMessage> msgs = ex.getMessageList();
        List<String> messages = new ArrayList<>();
        if (msgs != null) {
            for (BaseMessage message : msgs) {
                messages.add(messageSource.getMessage(message.getMessageCode(), message.getMessageArgs(), LocaleContextHolder.getLocale()));
            }
        }
        BaseResponse response = new BaseResponse(BaseResponse.STATUS_ERROR);
        response.setMessage(messages);
        return response;
    }

    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseResponse bindingException(BindException ex) {
        List<String> messages = new ArrayList<>();
        if (ex.hasErrors()) {
            List<ObjectError> errors = ex.getAllErrors();
            for (ObjectError error : errors) {
                messages.add(error.getDefaultMessage());
            }
        }
        BaseResponse response = new BaseResponse(BaseResponse.STATUS_ERROR);
        response.setMessage(messages);
        return response;
    }
}
