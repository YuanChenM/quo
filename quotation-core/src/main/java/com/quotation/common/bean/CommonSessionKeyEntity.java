/**
 * YMOPOF13ResultEntity.java
 * 
 * @screen YMOPOF13
 * 
 */
package com.quotation.common.bean;

import com.quotation.core.base.BaseEntity;

/**
 * YMOPOF13ResultEntity.
 */
public class CommonSessionKeyEntity extends BaseEntity {

    /** serialVersionUID */
    private static final long serialVersionUID = -8248719555407540054L;
    
    /** session key */
    private String sessionKey;

    /**
     * Get the sessionKey.
     *
     * @return sessionKey
     */
    public String getSessionKey() {
        return this.sessionKey;
    }

    /**
     * Set the sessionKey.
     *
     * @param sessionKey sessionKey
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
