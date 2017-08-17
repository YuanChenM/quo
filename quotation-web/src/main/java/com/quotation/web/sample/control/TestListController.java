package com.quotation.web.sample.control;

import com.quotation.core.base.BaseController;
import com.quotation.core.bean.BaseParam;
import com.quotation.web.sample.entity.SampleEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuan_chen1 on 2017/7/26.
 * TestController
 */
@Controller
public class TestListController extends BaseController {

    @RequestMapping(value = "/sp/test/list1")
    public String init1(Model model, HttpServletRequest request) {

        BaseParam param = new BaseParam();
        List<SampleEntity> samples = new ArrayList<>();

        SampleEntity entity;
        entity = new SampleEntity();
        entity.setSampleId(1);
        entity.setSampleCode("Code0011");
        entity.setSampleContent("111111");
        samples.add(entity);
        entity = new SampleEntity();
        entity.setSampleId(2);
        entity.setSampleCode("Code0021");
        entity.setSampleContent("111111");
        samples.add(entity);
        entity = new SampleEntity();
        entity.setSampleId(3);
        entity.setSampleCode("Code0031");
        entity.setSampleContent("1111");
        samples.add(entity);

        model.addAttribute("samples", samples);
        return "sample/TestList";
    }

    @RequestMapping(value = "/sp/test/list2")
    public String init2(Model model, HttpServletRequest request) {

        BaseParam param = new BaseParam();
        List<SampleEntity> samples = new ArrayList<>();

        SampleEntity entity;
        entity = new SampleEntity();
        entity.setSampleId(1);
        entity.setSampleCode("2222");
        entity.setSampleContent("22222");
        samples.add(entity);
        entity = new SampleEntity();
        entity.setSampleId(2);
        entity.setSampleCode("3333");
        entity.setSampleContent("33333");
        samples.add(entity);
        entity = new SampleEntity();
        entity.setSampleId(3);
        entity.setSampleCode("444444");
        entity.setSampleContent("4444444");
        samples.add(entity);

        model.addAttribute("samples", samples);
        return "sample/TestList";
    }
}
