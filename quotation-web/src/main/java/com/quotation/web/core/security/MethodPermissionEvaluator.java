package com.quotation.web.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Created by yang_shoulai on 8/11/2017.
 */
public class MethodPermissionEvaluator implements PermissionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(MethodPermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        UserContext user = getUserContext(authentication);
        if (user != null) {
            log.debug(String.format("check whether user [%s] has permission [%s] on domain object [%s]", user.getUsername(), permission, targetDomainObject));
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserContext user = getUserContext(authentication);
        if (user != null) {
            log.debug(String.format("check whether user [%s] has permission [%s] on target type [%s] with target id [%s]", user.getUsername(), permission, targetType, targetId));
        }
        return false;
    }

    private UserContext getUserContext(Authentication authentication) {
        return (UserContext) authentication.getPrincipal();
    }

}
