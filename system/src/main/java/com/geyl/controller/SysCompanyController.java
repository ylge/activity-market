package com.geyl.controller;

import com.geyl.annotation.Log;
import com.geyl.base.BaseController;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysCompany;
import com.geyl.service.SysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author geyl
 * @Title: SysCompanyController
 * @Package com.geyl.controller
 * @Description: 公司管理
 * @date 2018-5-18 13:40
 */
@Controller
@RequestMapping(value = "system/company/")
public class SysCompanyController extends BaseController {

    @Autowired
    private SysCompanyService sysCompanyService;

    /**
    　* @description:公司列表
    　* @param
    　* @return  
    　* @author geyl
    　* @date 2018-5-22 13:34 
    　*/
    @GetMapping(value = "list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/company/list");
        return modelAndView;
    }

    @GetMapping(value = "page")
    public @ResponseBody
    PageResult<SysCompany> page(SysCompany sysCompany){
        return sysCompanyService.pageList(sysCompany) ;
    }
    /**
    　* @description:公司新增
    　* @author geyl
    　* @date 2018-5-22 13:35 
    　*/
    @GetMapping(value = "add")
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/company/add");
        return modelAndView;
    }

    /**
     　* @description:公司编辑
     　* @author geyl
     　* @date 2018-5-22 13:35
     　*/
    @GetMapping(value = "edit/{companyId}")
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable String companyId) {
        modelAndView.setViewName("/system/company/edit");
        modelAndView.addObject("company",sysCompanyService.selectByPrimaryKey(companyId));
        return modelAndView;
    }

    /**
     　* @description:新增公司
     　* @param
     　* @return
     　* @author geyl
     　* @date 2018-5-22 13:35
     　*/
    @Log(desc="公司信息新增")
    @PostMapping(value = "add")
    public @ResponseBody
    Result addCompany(SysCompany sysCompany ){
        return sysCompanyService.save(sysCompany);
    }

    /**
     　* @description:编辑公司
     　* @param
     　* @return
     　* @author geyl
     　* @date 2018-5-22 13:35
     　*/
    @Log(desc="公司信息编辑")
    @PostMapping(value = "update")
    public @ResponseBody
    Result updateCompany(SysCompany sysCompany ){
        return sysCompanyService.save(sysCompany);
    }


}
