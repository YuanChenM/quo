package com.quotation.web.core.mapper;

import com.quotation.web.core.bean.RoleInfo;
import com.quotation.web.core.entity.AccessResource;
import com.quotation.web.core.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yang_shoulai on 8/14/2017.
 */
public interface UserMapper {

    User findByLoginId(String loginId);

    List<RoleInfo> findRoleByLoginId(String loginId);

    List<RoleInfo> findRole(@Param("loginId") String loginId, @Param("officeCode") String officeCode, @Param("sectionCode") String sectionCode);

    List<AccessResource> findAccessResource(@Param("loginId") String loginId, @Param("officeCode") String officeCode, @Param("sectionCode") String sectionCode);
}
