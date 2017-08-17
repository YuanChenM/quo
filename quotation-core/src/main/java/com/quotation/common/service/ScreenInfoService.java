/**
 * TnmUomService.java
 * 
 * @screen common
 * 
 */
package com.quotation.common.service;

import com.quotation.common.bean.TnmAuthorizationEx;
import com.quotation.core.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TnmUomService.
 * 
 */
@Service("common.ScreenInfoService")
public class ScreenInfoService extends BaseService {

    /**
     * get all screen information.
     * 
     * @return uom digits list
     */
    public List<TnmAuthorizationEx> selectAllScreenAuthInfo() {
        TnmAuthorizationEx screenParam = new TnmAuthorizationEx();
        List<TnmAuthorizationEx> list = baseMapper.select(this.getSqlId("getScreenInfo"), screenParam);
        return list;
    }
}