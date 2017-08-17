package com.quotation.web.core.control;

import com.quotation.web.core.security.UserContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by yang_shoulai on 8/16/2017.
 */
@Controller
public class ChangeOfficeController extends BaseController {

    @RequestMapping(value = "/changeOffice")
    public String changeOffice(@RequestParam("officeCode") String officeCode,
                               @RequestParam("sectionCode") String sectionCode) {
        UserContext userContext = getUserContext();
        userContext.setActiveOffice(officeCode);
        userContext.setActiveSection(sectionCode);
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userContext.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return "redirect:main";
    }
}
