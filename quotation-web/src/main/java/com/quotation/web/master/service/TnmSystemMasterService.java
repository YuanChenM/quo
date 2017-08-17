package com.quotation.web.master.service;

import com.quotation.core.base.BaseService;
import com.quotation.core.bean.BaseParam;
import com.quotation.web.master.entity.TnmSystemMasterEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yang_shoulai on 8/8/2017.
 */
@Service
public class TnmSystemMasterService extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(TnmSystemMasterService.class);

    public List<TnmSystemMasterEntity> listAll(BaseParam param, List<Integer> idList) {
        param.setFilter("idList", idList);
        return this.baseMapper.select(this.getSqlId("listAll"), param);
    }


    public List<HashMap<String, Object>> loadMasterSpec(String masterName) {
        String[] words = masterName.split(" ");
        StringBuilder builder = new StringBuilder("load");
        for (String w : words) {
            if (!StringUtils.isEmpty(w)) {
                builder.append(w.substring(0, 1).toUpperCase()).append(w.substring(1));
            }
        }
        String sqlId = builder.toString();
        log.debug(String.format("get the master data sql id = %s", sqlId));
        if (sqlId.length() > 4) {
            //return this.baseMapper.getSqlSession().selectList(this.getSqlId(sqlId));
        }
        return Collections.emptyList();
    }

}
