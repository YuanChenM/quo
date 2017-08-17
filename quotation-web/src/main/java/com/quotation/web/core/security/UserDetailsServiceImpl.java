package com.quotation.web.core.security;

import com.quotation.web.core.bean.RoleInfo;
import com.quotation.web.core.entity.User;
import com.quotation.web.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by yang_shoulai on 8/11/2017.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.debug("load user from database with username equals {}", loginId);
        User user = userService.findByLoginId(loginId);
        if (user == null) throw new UsernameNotFoundException(String.format("user with login id equals %s not found", loginId));
        List<RoleInfo> roles = this.userService.findRoleInfoByLoginId(user.getLoginId());
        UserContext context = new UserContext();
        BeanUtils.copyProperties(user, context);
        context.setAuthorities(roles);
        context.setActiveOffice(user.getDefaultOffice());
        context.setActiveSection(user.getDefaultSection());
        return context;
    }
}
