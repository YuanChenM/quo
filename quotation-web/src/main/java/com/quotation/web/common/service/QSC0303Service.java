package com.quotation.web.common.service;

import com.quotation.core.base.BaseService;
import com.quotation.core.bean.BaseParam;
import com.quotation.web.common.entity.QSC0303Entity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhu_yingjie on 2017/8/8.
 */
@Service("qsc0303Service")
@Transactional
public class QSC0303Service extends BaseService{

    public List<QSC0303Entity> getInformationList(BaseParam param) {
        return this.baseMapper.select(this.getSqlId("getInformationList"), param);
    }
}
