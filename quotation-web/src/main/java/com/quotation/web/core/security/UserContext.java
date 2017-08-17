package com.quotation.web.core.security;

import com.quotation.web.core.bean.RoleInfo;
import com.quotation.web.core.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yang_shoulai on 8/11/2017.
 */
public class UserContext extends User implements UserDetails {

    private static final String SEPARATOR = "$";

    private Map<String, Collection<GrantedAuthority>> authoritiesCache = new HashMap<>();

    //User Office, key -> office code, value -> section code
    private Map<String, List<String>> officeCache = new HashMap<>();

    private String activeOffice;

    private String activeSection;

    public void setAuthorities(Collection<RoleInfo> roles) {
        if (roles == null || roles.isEmpty()) return;
        for (RoleInfo role : roles) {
            String key = getCacheKey(role.getOfficeCode(), role.getSectionCode());
            if (!authoritiesCache.containsKey(key)) {
                authoritiesCache.put(key, new ArrayList<GrantedAuthority>());
            }
            if (!officeCache.containsKey(role.getOfficeCode())) {
                officeCache.put(role.getOfficeCode(), new ArrayList<String>());
            }
            Collection<GrantedAuthority> authorities = authoritiesCache.get(key);
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            if (role.getResourceCodes() != null) {
                for (String resource : role.getResourceCodes()) {
                    authorities.add(new SimpleGrantedAuthority(resource));
                }
            }
            List<String> sectionCodes = officeCache.get(role.getOfficeCode());
            sectionCodes.add(role.getSectionCode());
        }
    }


    public String getActiveOffice() {
        return activeOffice;
    }

    public void setActiveOffice(String activeOffice) {
        this.activeOffice = activeOffice;
    }

    public String getActiveSection() {
        return activeSection;
    }

    public void setActiveSection(String activeSection) {
        this.activeSection = activeSection;
    }

    private String getCacheKey(String officeCode, String sectionCode) {
        return officeCode + SEPARATOR + sectionCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (StringUtils.isEmpty(activeOffice) || StringUtils.isEmpty(activeSection)) {
            this.activeOffice = this.getDefaultOffice();
            this.activeSection = this.getDefaultSection();
        }
        return this.authoritiesCache.get(getCacheKey(this.activeOffice, this.activeSection));
    }

    public Map<String, List<String>> getOffices() {
        return officeCache;
    }

    @Override
    public String getPassword() {
        return getPwd();
    }

    @Override
    public String getUsername() {
        return getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
