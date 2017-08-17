package com.quotation.web.common.control;

import com.quotation.common.consts.QuotationConst;
import com.quotation.common.util.SessionInfoManager;
import com.quotation.web.core.control.BaseController;
import com.quotation.core.bean.BaseParam;
import com.quotation.core.util.StringUtil;
import com.quotation.web.common.entity.QSC0302Entity;
import com.quotation.web.common.service.QSC0302Service;
import com.quotation.web.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhu_yingjie on 2017/8/10.
 */
@Controller
@RequestMapping("/QSC0302")
public class QSC0302Controller extends BaseController{

    @Autowired
    private QSC0302Service qsc0302Service;

    @RequestMapping("/{id}")
    public String view(@PathVariable(value = "id") Integer id, Model model) {
        QSC0302Entity qsc0302Entity = null;
        if (id == -1) {
            model.addAttribute("dataModel", QuotationConst.ScreenMode.ADD);
            qsc0302Entity = new QSC0302Entity();
        } else {
            qsc0302Entity = findEntityById(id);
            model.addAttribute("dataModel", QuotationConst.ScreenMode.VIEW);
        }
        model.addAttribute("qsc0302Entity", qsc0302Entity);
        model.addAttribute("isSuccess", false);
        return "common/QSC0302";
    };

    @RequestMapping("/save")
    public String save(@ModelAttribute("QSC0302Entity") QSC0302Entity qsc0302Entity, Model model) {
        saveOrUpdate(qsc0302Entity, model);
        return "common/QSC0302";
    }

    @RequestMapping("update")
    public String update(@ModelAttribute("QSC0302Entity") QSC0302Entity qsc0302Entity, Model model) {
        saveOrUpdate(qsc0302Entity, model);
        return "common/QSC0302";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model) {
        QSC0302Entity qsc0302Entity = findEntityById(id);
        setModel(model, qsc0302Entity, QuotationConst.ScreenMode.EDIT, false);
        return "common/QSC0302";
    }

    private QSC0302Entity findEntityById(Integer id) {
        BaseParam param = new BaseParam();
        param.setFilter("informationId", id);
        QSC0302Entity qsc0302Entity = qsc0302Service.findEntityById(param);
        return qsc0302Entity;
    }

    private void saveOrUpdate(QSC0302Entity qsc0302Entity, Model model) {
        User user = getUserContext();
        Integer userId  = user.getUserId();
        qsc0302Entity.setCreateBy(userId);
        qsc0302Entity.setCreateDate(new Date());
        qsc0302Entity.setUpdateBy(userId);
        qsc0302Entity.setUpdateDate(new Date());
        if (null == qsc0302Entity.getInformationId()) {
            qsc0302Entity.setInformationId(qsc0302Service.getInformationId());
            qsc0302Service.save(qsc0302Entity);
        } else {
            qsc0302Service.update(qsc0302Entity);
        }
        setModel(model, qsc0302Entity, QuotationConst.ScreenMode.VIEW, true);
    }

    private void setModel(Model model, QSC0302Entity qsc0302Entity, String dataModel, boolean isSuccess) {
        model.addAttribute("qsc0302Entity", qsc0302Entity);
        model.addAttribute("dataModel", dataModel);
        model.addAttribute("isSuccess", isSuccess);
    }
}
