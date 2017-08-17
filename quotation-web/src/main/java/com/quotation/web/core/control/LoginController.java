/**
 * @screen login
 */
package com.quotation.web.core.control;

import com.quotation.common.bean.UserInfo;
import com.quotation.common.consts.QuotationConst;
import com.quotation.common.util.SessionInfoManager;
import com.quotation.core.bean.BaseResult;
import com.quotation.core.util.CookieUtils;
import com.quotation.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * System automatically obtains the Login ID value from local host cookies file.
     *
     * @param model   the form bean
     * @param request request
     * @return the return page
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String init(Model model, HttpServletRequest request) {
        logger.debug("login");
        return "core/login";
    }

    /**
     * do user login check.
     *
     * @param loginId  loginId
     * @param request  request
     * @param response response
     * @return checkLoginInfo
     * @throws Exception e
     */
    @RequestMapping(value = "/login/check", method = {RequestMethod.POST})
    @ResponseBody
    public BaseResult<String> checkLoginInfo(@RequestParam("loginId") String loginId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        BaseResult<String> ret = new BaseResult<>();
        // set login
        if (!StringUtil.isEmpty(loginId)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setLoginId(loginId);
            if (StringUtil.equals(userInfo.getLoginId(), "en")) {
                userInfo.setUserId(1);
                userInfo.setUserName("Administrator");
                userInfo.setDefaultLang(QuotationConst.Language.ENGLISH.getCode());
                userInfo.setLanguage(QuotationConst.Language.ENGLISH);
            } else {
                userInfo.setUserId(2);
                userInfo.setUserName("管理员");
                userInfo.setDefaultLang(QuotationConst.Language.CHINESE.getCode());
                userInfo.setLanguage(QuotationConst.Language.CHINESE);
            }
            // set session
            SessionInfoManager context = SessionInfoManager.getContextInstance(request);
            context.clear();
            context.setLoginInfo(userInfo);

            // set cookie
            CookieUtils utils = new CookieUtils(request, response);
            utils.addCookie(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, context.getLoginInfo().getDefaultLang().toString());
        }

        return ret;
    }
}
