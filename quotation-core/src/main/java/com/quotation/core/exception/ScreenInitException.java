/**
 * ScreenInitException.java
 * 
 * @screen core
 * 
 */
package com.quotation.core.exception;

import com.quotation.core.util.StringUtil;

/**
 * The exception when download file is fail.
 */
public class ScreenInitException extends BusinessException {

    /** serialVersionUID */
    private static final long serialVersionUID = -3458922853154805745L;

    /** default callback script */
    private static final String DEFAULT_CALLBACK = "parent.DWZ.downloadDone(''{0}'');";

    /** callback script */
    private String callback;

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     * 
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *        method.
     */
    public ScreenInitException(String message) {
        super(message);
        this.callback = StringUtil.formatMessage(DEFAULT_CALLBACK, message);
    }

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     * 
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *        method.
     * @param callback callback script
     */
    public ScreenInitException(String message, String callback) {
        super(message);
        this.callback = callback;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with <code>cause</code> is <i>not</i> automatically incorporated in this
     * exception's detail message.
     * 
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     *        value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public ScreenInitException(String message, Throwable cause) {
        super(message, cause);
        this.callback = StringUtil.formatMessage(DEFAULT_CALLBACK, message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with <code>cause</code> is <i>not</i> automatically incorporated in this
     * exception's detail message.
     * 
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param callback callback script
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     *        value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public ScreenInitException(String message, String callback, Throwable cause) {
        super(message, cause);
        this.callback = callback;
    }

    /**
     * <p>
     * get the callback script.
     * </p>
     * 
     * @return callback script
     */
    public String getCallback() {
        return this.callback;
    }

    /**
     * <p>
     * set the callback script.
     * </p>
     * 
     * @param callback callback script
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }
}
