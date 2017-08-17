/**
 * TnmUomService.java
 * 
 * @screen common
 * 
 */
package com.quotation.common.service;

import com.quotation.common.bean.UomData;
import com.quotation.core.base.BaseService;
import com.quotation.core.bean.BaseParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TnmUomService.
 * 
 */
@Service("common.TnmUomService")
public class TnmUomService extends BaseService {

    /**
     * get all decimal digits.
     * 
     * @return uom digits list
     */
    public List<UomData> selectAllDigits() {
        BaseParam param = new BaseParam();
        List<UomData> list = baseMapper.select("com.quotation.common.service.CommonService.getDecimalDigits", param);
        return list;
    }
}