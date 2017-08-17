package com.quotation.web.core.control;

import com.quotation.web.core.util.JsonUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yang_shoulai on 8/9/2017.
 */
public class BaseResponse {

    public static final int STATUS_SUCCESS = 200;

    public static final int STATUS_ERROR = 300;

    public static final int STATUS_TIMEOUT = 301;

    private int statusCode;

    private int errorCode;

    private List<String> message = new ArrayList<>();

    private String navTabId;

    private String rel;

    private String callbackType;  //"closeCurrent"

    private String forwardUrl;

    public BaseResponse(int statusCode) {
        this(statusCode, 0);
    }

    public BaseResponse(int statusCode, String... msg) {
        this(statusCode, 0, msg);
    }

    public BaseResponse(int statusCode, int errorCode, String... msg) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        if (msg != null && msg.length > 0) {
            this.message.addAll(Arrays.asList(msg));
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public void addMessage(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            message.add(msg);
        }
    }

    public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }
}
