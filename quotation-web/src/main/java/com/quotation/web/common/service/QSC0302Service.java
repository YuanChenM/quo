package com.quotation.web.common.service;

import com.quotation.core.base.BaseService;
import com.quotation.core.bean.BaseParam;
import com.quotation.web.common.entity.QSC0302Entity;
import org.springframework.stereotype.Service;

/**
 * Created by zhu_yingjie on 2017/8/10.
 */
@Service

public class QSC0302Service extends BaseService{
    public QSC0302Entity findEntityById(BaseParam param) {
        return this.baseMapper.findOne(this.getSqlId("getInformationById"), param);
    }

    public void save(QSC0302Entity qsc0302Entity) {
        this.baseMapper.insert(this.getSqlId("saveInformation"), qsc0302Entity);
    }

    public void update(QSC0302Entity qsc0302Entity) {
        this.baseMapper.update(this.getSqlId("updateInformation"), qsc0302Entity);
    }

    public Integer getInformationId() {
        QSC0302Entity qsc0302Entity = this.baseMapper.findOne(this.getSqlId("getMaxId"), new BaseParam());
        return qsc0302Entity.getInformationId();
    }
}
