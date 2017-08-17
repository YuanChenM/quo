package com.quotation.core.exception;

import com.quotation.common.bean.ExceptionAttach;
import com.quotation.core.bean.BaseMessage;

import java.util.List;

/**
 * 
 * BatchException.
 * 
 */
public class BatchException extends RuntimeException {

 
    /** serialVersionUID */
    private static final long serialVersionUID = -7057863165020566265L;

    /** message list */
    private List<BaseMessage> messageList;

    /** message list */
    private List<ExceptionAttach> attachedInfoList;

    /**
     * 
     * The Constructors Method.
     *
     * @param attachedInfoList
     */
    public BatchException() {}

    /**
     * 
     * The Constructors Method.
     *
     * @param attachedInfoList attachedInfoList
     */
    public BatchException(List<ExceptionAttach> attachedInfoList) {
        this.attachedInfoList = attachedInfoList;
    }
    
    /**
     * getMessageList
     * 
     * @return List<BaseInterfaceEntity>
     */
    public List<BaseMessage> getMessageList() {
        return this.messageList;
    }

    /**
     * setMessageList
     * 
     * @param messageList messageList
     */
    public void setMessageList(List<BaseMessage> messageList) {
        this.messageList = messageList;
    }

    /**
     * Get the attachedInfoList.
     *
     * @return attachedInfoList
     */
    public List<ExceptionAttach> getAttachedInfoList() {
        return this.attachedInfoList;
    }

    /**
     * Set the attachedInfoList.
     *
     * @param attachedInfoList attachedInfoList
     */
    public void setAttachedInfoList(List<ExceptionAttach> attachedInfoList) {
        this.attachedInfoList = attachedInfoList;
        
    }
}
