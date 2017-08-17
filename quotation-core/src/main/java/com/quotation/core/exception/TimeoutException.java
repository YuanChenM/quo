/**
 * @screen core
 * 
 */
package com.quotation.core.exception;

import com.quotation.core.consts.MessageCodeConst;

/**
 * The exception for session time out.
 * 
 */
public class TimeoutException extends BusinessException {
    private static final long serialVersionUID = 3358078457294620782L;

    /**
     * Constructs a session timeout exception.
     * 
     */
    public TimeoutException() {
        super(MessageCodeConst.E0003);
    }
}
