package com.quotation.web.core.exception;

import com.quotation.web.core.constant.MessageCode;
import com.quotation.web.core.control.BaseResponse;
import com.quotation.core.bean.BaseMessage;
import com.quotation.core.consts.MessageCodeConst;
import com.quotation.core.consts.StringConst;
import com.quotation.core.exception.BusinessException;
import com.quotation.core.util.StringUtil;
import com.quotation.core.util.interceptor.ExceptionResolver;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;

/**
 * Created by yang_shoulai on 8/14/2017.
 */
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {

    private final static String JSON_EXT = "json";

    private final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    private Map<String, Integer> errorCodes = new HashMap<>();

    private Integer defaultErrorCode;

    private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            Integer statusCode = determineStatusCode(request, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
            return getModelAndView(viewName, ex, request, response);
        } else {
            return null;
        }
    }

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        if (this.logger != null && this.logger.isErrorEnabled()) {
            List<String> messages = resolveExceptionMessages(ex);
            if (messages != null) {
                StringBuilder builder = new StringBuilder();
                for (String message : messages) {
                    builder.append(StringConst.SEMICOLON).append(message);
                }
                if (builder.length() > 0) {
                    String msg = builder.toString().substring(1);
                    logger.error(msg, ex);
                }
            }
        }
    }

    protected ModelAndView getModelAndView(String viewName, Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = super.getModelAndView(viewName, ex, request);
        String filename = WebUtils.extractFullFilenameFromUrlPath(viewName);
        String ext = StringUtils.getFilenameExtension(filename);
        List<String> messages = resolveExceptionMessages(ex);
        if (JSON_EXT.equalsIgnoreCase(ext)) {
            BaseResponse res = new BaseResponse(BaseResponse.STATUS_ERROR, determineErrorCode(request, ex));
            res.setMessage(messages);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(res);
                if (!StringUtil.isEmpty(json)) {
                    modelView.addObject("json", json);
                }
            } catch (IOException e) {
                logger.error("exception when write object {} to json ", res);
            }
        } else {
            modelView.addObject("messages", messages);
        }
        return modelView;
    }

    protected Integer determineErrorCode(HttpServletRequest request, Exception ex) {
        String exName = ex.getClass().getName();
        if (this.errorCodes.containsKey(exName)) {
            return this.errorCodes.get(exName);
        }
        return this.defaultErrorCode;
    }


    /**
     * resolve i18n exception messages
     *
     * @param ex the exception
     * @return message list
     */
    private List<String> resolveExceptionMessages(Exception ex) {
        List<String> messages = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        if (ex instanceof MaxUploadSizeExceededException) {
            messages.add(messageSource.getMessage(MessageCode.System.MAX_FILE_SIZE, null, locale));
        } else if (ex instanceof FileNotFoundException) {
            messages.add(messageSource.getMessage(MessageCode.System.FILE_NOT_FOUND, null, locale));
        } else if (ex instanceof AccessDeniedException) {
            messages.add(messageSource.getMessage(MessageCode.System.ACCESS_DENIED, null, locale));
        } else if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            List<BaseMessage> msgs = businessException.getMessageList();
            if (msgs != null) {
                for (BaseMessage message : msgs) {
                    messages.add(messageSource.getMessage(message.getMessageCode(), message.getMessageArgs(), locale));
                }
            }
        } else if (ex instanceof BindException) {
            BindException bindException = (BindException) ex;
            if (bindException.hasErrors()) {
                List<ObjectError> errors = bindException.getAllErrors();
                for (ObjectError error : errors) {
                    messages.add(error.getDefaultMessage());
                }
            }
        } else {
            messages.add(messageSource.getMessage(MessageCode.System.SERVER_ERROR, null, locale));
        }
        return messages;
    }

    public void setErrorCodes(Map<String, Integer> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public void setDefaultErrorCode(Integer defaultErrorCode) {
        this.defaultErrorCode = defaultErrorCode;
    }

}
