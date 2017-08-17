/**
 * MasterManager.java
 *
 * @screen common.
 * 
 */
package com.quotation.common.util;

import com.quotation.common.bean.UomData;
import com.quotation.common.service.TnmUomService;
import com.quotation.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MasterManager.
 *
 */
@Component
public class MasterManager {

    /** logger */
    private static Logger logger = LoggerFactory.getLogger(MasterManager.class);

    private static Map<String, Integer> uomMap;

//    @Autowired
//    TnmUomService uomService;

    /**
     * The Constructors Method.
     *
     */
    public MasterManager() {}

    /**
     * Initializer method. Get TnmUom information from database and store it in uomMap for further
     * use.
     *
     */
    @PostConstruct
    public void initMap() {
        logger.debug("method initMap is start");

        if (uomMap == null) {
            uomMap = new HashMap<String, Integer>();
        }

//        List<UomData> list = uomService.selectAllDigits();

//        for (UomData data : list) {
//
//            if (!uomMap.containsKey(data.getUomCode())) {
//                uomMap.put(data.getUomCode(), data.getDecimalDigits());
//            }
//        }

        logger.debug("method initMap is end");
    }

    /**
     * Get all UOM Digits.
     * 
     * @return UOM digits
     */
    public static Map<String, Integer> getAllUomDigits() {

        return uomMap;
    }

    /**
     * Get UOM Digits by UOM code.
     * 
     * @param uomCode UOM code
     * @return UOM digits
     */
    public static Integer getUomDigits(String uomCode) {
        // logger.debug("method getDigits is start");
        if (StringUtil.isEmpty(uomCode)) {
            logger.debug("method getDigits is null");
            return 0;
        }
        Integer digits = uomMap.get(uomCode);
        if (digits == null) {
            return 0;
        }

        // logger.debug("method getDigits is end");
        return digits;
    }
}
