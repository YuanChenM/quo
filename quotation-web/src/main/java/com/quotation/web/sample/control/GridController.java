package com.quotation.web.sample.control;

import com.quotation.core.base.BaseController;
import com.quotation.core.tag.Column;
import com.quotation.web.sample.entity.GridEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu_yingjie on 2017/8/15.
 */
@Controller
@RequestMapping("/grid")
public class GridController extends BaseController{

    @RequestMapping("")
    public String view(Model model) {
        List<GridEntity> gridList = new ArrayList<>();
        List<GridEntity> subList = new ArrayList<>();
        GridEntity entity = new GridEntity();
        entity.setNo("No1");
        entity.setDate("2017-08-01");
        entity.setRemark("ABC");
        entity.setSellingTo("SGP");

        GridEntity subEntity = new GridEntity();
        subEntity.setNo("No1-1");
        subEntity.setDate("2017-08-01");
        subEntity.setRemark("ABCD");
        subEntity.setSellingTo("SGP");

        subList.add(subEntity);
        entity.setSubList(subList);
        gridList.add(entity);

        entity = new GridEntity();
        entity.setNo("No2");
        entity.setDate("2017-08-01");
        entity.setRemark("bnc");
        entity.setSellingTo("TH");
        gridList.add(entity);

        model.addAttribute("items", gridList);
        List<Column> columns = new ArrayList<>();
        Column column = new Column("No", "no", "/grid/", null);
        columns.add(column);
        column = new Column("Date", "date", null, null);
        columns.add(column);
        column = new Column("Remark", "remark", null, null);
        columns.add(column);
        column = new Column("SellingTo", "sellingTo", null, null);
        columns.add(column);
        model.addAttribute("columns", columns);
        return "sample/grid";
    }
}
