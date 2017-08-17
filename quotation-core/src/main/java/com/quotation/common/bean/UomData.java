/**
 * UomData.java
 * 
 * @screen common
 * 
 */
package com.quotation.common.bean;

import com.quotation.core.base.BaseEntity;
import com.quotation.core.util.StringUtil;

/**
 * The entity for master data combobox.
 * 
 */
public class UomData extends BaseEntity {

    /** serialVersionUID */
    private static final long serialVersionUID = -2414902728231422781L;
    /** uomCode */
    private String uomCode;
    /** decimalDigits */
    private Integer decimalDigits;
    
    /**
     * Get the uomCode.
     *
     * @return uomCode
     */
    public String getUomCode() {
        return this.uomCode;
    }


    /**
     * Set the uomCode.
     *
     * @param uomCode uomCode
     */
    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }


    /**
     * Get the decimalDigits.
     *
     * @return decimalDigits
     */
    public Integer getDecimalDigits() {
        return this.decimalDigits;
    }


    /**
     * Set the decimalDigits.
     *
     * @param decimalDigits decimalDigits
     */
    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }


    /**
     * toString
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return StringUtil.formatMessage("UomData [uomCode={}, decimalDigits={}]", this.uomCode, this.decimalDigits);
    }

}
