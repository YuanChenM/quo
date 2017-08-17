package com.quotation.web.common.control;

import com.quotation.common.util.SessionInfoManager;
import com.quotation.core.base.BaseController;
import com.quotation.core.bean.BaseParam;
import com.quotation.core.bean.BaseResult;
import com.quotation.web.common.entity.QSC0303Entity;
import com.quotation.web.common.service.QSC0303Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhu_yingjie on 2017/8/8.
 */
@Controller
@RequestMapping("/QSC0303")
public class QSC0303Controller extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(QSC0303Controller.class);

    @Autowired
    private QSC0303Service qsc0303Service;

    @RequestMapping(method = RequestMethod.GET)
    public String getInformationList(Model model) {
        SessionInfoManager session = SessionInfoManager.getContextInstance();
        String loginId = session.getLoginInfo().getLoginId();
        BaseParam param = new BaseParam();
        param.setLoginId(loginId);
        List<QSC0303Entity> informationList = qsc0303Service.getInformationList(param);
        model.addAttribute("informationList", informationList);
        return "common/QSC0303";
    }
}
