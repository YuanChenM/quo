package com.quotation.web.common.service;

import com.quotation.core.base.BaseService;
import com.quotation.core.bean.BaseParam;
import com.quotation.core.bean.ObjectParam;
import com.quotation.core.bean.PageParam;
import com.quotation.core.bean.PageResult;
import com.quotation.web.common.entity.QSC0301Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhu_yingjie on 2017/8/8.
 */
@Service("qsc0301Service")
@Transactional
public class QSC0301Service extends BaseService{

    public Page<QSC0301Entity> getPageList(PageParam param, Pageable pageable) {
        PageResult<QSC0301Entity> result = super.getPageList(param);
        Page<QSC0301Entity> page = new PageImpl<QSC0301Entity>(result.getDatas(), pageable, result.getTotalCount());
        return page;
    }

    public int deleteInformation(ObjectParam param) {
        return this.baseMapper.delete(this.getSqlId("deleteInformation"), param);
    }
}
