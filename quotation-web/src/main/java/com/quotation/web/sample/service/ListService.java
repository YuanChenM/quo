package com.quotation.web.sample.service;

import com.quotation.core.base.BaseService;
import com.quotation.core.bean.BaseParam;
import com.quotation.web.sample.entity.SampleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by yuan_chen1 on 2017/7/19.
 *
 * ListService
 */
@Service
public class ListService extends BaseService {
    /** logger. */
    private static Logger logger = LoggerFactory.getLogger(ListService.class);

    public List<SampleEntity> getSamples(BaseParam baseParam) {
        logger.debug("getSamples begin");
        List<SampleEntity> sampleEntities = super.baseMapper.select(super.getSqlId("findList"), baseParam);
        logger.debug("getSamples over");
        return sampleEntities;
    }

    public SampleEntity getSample(BaseParam baseParam) {
        logger.debug("getSample begin");
        SampleEntity result = super.baseMapper.findOne(super.getSqlId("findOne"), baseParam);
        logger.debug("getSample over");
        return result;
    }

    public int getMaxId(){
        SampleEntity sampleEntity = super.baseMapper.findOne(super.getSqlId("findMaxId"), new BaseParam());
        int maxId = sampleEntity.getSampleId();
        return maxId;
    }

    public int addSample(SampleEntity entity) {
        logger.debug("addSample begin");

        String uuid = UUID.randomUUID().toString();
        entity.setSampleUid(uuid);

        entity.setSampleId(this.getMaxId());

        int result = super.baseMapper.insert(super.getSqlId("insertOne"), entity);
        logger.debug("addSample over");
        return result;
    }

    public int modifySample(SampleEntity entity) {
        logger.debug("modifySample begin");

        int result = super.baseMapper.update(super.getSqlId("updateOne"), entity);
        logger.debug("modifySample over");
        return result;
    }

    public int removeSample(SampleEntity entity) {
        logger.debug("modifySample begin");

        int result = super.baseMapper.delete(super.getSqlId("deleteOne"), entity);
        logger.debug("modifySample over");
        return result;
    }
}
