package com.quotation.web.common.control;

import com.quotation.core.base.BaseController;
import com.quotation.core.bean.BaseParam;
import com.quotation.core.bean.ObjectParam;
import com.quotation.core.bean.PageParam;
import com.quotation.core.bean.PageResult;
import com.quotation.web.common.condition.QSC0301Condition;
import com.quotation.web.common.entity.QSC0301Entity;
import com.quotation.web.common.service.QSC0301Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by zhu_yingjie on 2017/8/8.
 */
@Controller
@RequestMapping("/QSC0301")
public class QSC0301Controller extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(QSC0301Controller.class);

    @Autowired
    private QSC0301Service qsc0301Service;

    @RequestMapping("")
    public String view(Model model, @ModelAttribute("QSC0301Condition") QSC0301Condition qsc0301Condition,
            Pageable pageable, HttpServletRequest request) {
        PageParam param= new PageParam();
        if (qsc0301Condition.getTitle() != null) {
            param.setFilter("title", qsc0301Condition.getTitle());
        }
        if (qsc0301Condition.getInformation() != null) {
            param.setFilter("information", qsc0301Condition.getInformation());
        }
        if (qsc0301Condition.getDateFromStart() != null) {
            param.setFilter("dateFromStart", qsc0301Condition.getDateFromStart());
        }
        if (qsc0301Condition.getDateFromEnd() != null) {
            param.setFilter("dateFromEnd", qsc0301Condition.getDateFromEnd());
        }
        if (qsc0301Condition.getDateToStart() != null) {
            param.setFilter("dateToStart", qsc0301Condition.getDateToStart());
        }
        if (qsc0301Condition.getDateToEnd() != null) {
            param.setFilter("dateToEnd", qsc0301Condition.getDateToEnd());
        }
        param.setStart(pageable.getOffset());
        param.setLimit(pageable.getPageSize());
        Page<QSC0301Entity> informationList = qsc0301Service.getPageList(param, pageable);
        model.addAttribute("page", informationList);
        if ("true".equals(request.getParameter("isDelete"))) {
            model.addAttribute("isSuccess", true);
        } else {
            model.addAttribute("isSuccess", false);
        }
        return "common/QSC0301";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("informationIds") String informationIds, Model model) {
        if (informationIds != null) {
            List<String> informationIdList = Arrays.asList(informationIds.split(","));
            List<Integer> idList = new ArrayList<>();
            for(String id : informationIdList) {
                idList.add(Integer.parseInt(id));
            }
            ObjectParam param = new ObjectParam();
            param.setDatas(idList);
            qsc0301Service.deleteInformation(param);
            model.addAttribute("isSuccess", true);
        }
        return "forward:/QSC0301?isDelete=true";
    }
}
