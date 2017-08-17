package com.quotation.web.core.service;

import com.quotation.web.core.bean.RoleInfo;
import com.quotation.web.core.entity.AccessResource;
import com.quotation.web.core.entity.User;
import com.quotation.web.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yang_shoulai on 8/14/2017.
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findByLoginId(String loginId) {
        return userMapper.findByLoginId(loginId);
    }

    public List<RoleInfo> findRoleInfoByLoginId(String loginId) {
        return userMapper.findRoleByLoginId(loginId);
    }

    public List<AccessResource> findAccessResource(String loginId, String officeCode, String sectionCode) {
        return this.userMapper.findAccessResource(loginId, officeCode, sectionCode);
    }
}
