package com.quotation.web.core.control;

import com.quotation.core.bean.BaseParam;
import com.quotation.web.common.entity.QSC0303Entity;
import com.quotation.web.common.service.QSC0303Service;
import com.quotation.web.core.security.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by yang_shoulai on 8/16/2017.
 */
@Controller
public class MainController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private QSC0303Service qsc0303Service;

    /**
     * System automatically obtains the Login ID value from local host cookies file.
     *
     * @param model    the form bean
     * @param request  request
     * @param response response
     * @return the return page
     */
    @RequestMapping(value = "/main", method = {RequestMethod.GET})
    public String init(Model model, HttpServletRequest request, HttpServletResponse response) {
        UserContext userContext = getUserContext();
        Map<String, List<String>> offices = userContext.getOffices();
        BaseParam param = new BaseParam();
        param.setLoginId(userContext.getUsername());
        List<QSC0303Entity> informationList = qsc0303Service.getInformationList(param);
        model.addAttribute("informationList", informationList);
        model.addAttribute("offices", offices);
        return "core/main";
    }
}
