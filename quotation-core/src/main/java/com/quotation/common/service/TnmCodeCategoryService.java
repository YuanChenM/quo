/**
 * TnmCodeCategoryService.java
 * 
 * @screen common
 * 
 */
package com.quotation.common.service;

import com.quotation.common.entity.TnmCodeCategory;
import com.quotation.core.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CodeCategoryService.
 * 
 */
@Service("common.TnmCodeCategoryService")
public class TnmCodeCategoryService extends BaseService {
    
    /**
     * Query the data list by default sql.
     *
     * @return List<TnmCodeCategory> the result datas
     */
    public List<TnmCodeCategory> findAll() {
        String hql = "FROM TNM_CODE_CATEGORY ORDER BY LANGUAGE_FLAG, CODE_CATEGORY, CODE_VALUE";
        return baseDao.select(hql);
    }
 
}
