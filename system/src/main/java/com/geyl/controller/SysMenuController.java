package com.geyl.controller;

import com.geyl.annotation.Log;
import com.geyl.base.BaseController;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysMenu;
import com.geyl.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 菜单管理
 * 　* @author geyl
 * 　* @date 2018-5-18 13:15
 *
 */
@Controller
@RequestMapping("system/menu/")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService menuService;

    /**
     * 菜单列表
     *
     * @param modelAndView
     * @return
     */
    @GetMapping(value = "list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/menu/list");
        return modelAndView;
    }

    @GetMapping(value = "page")
    public @ResponseBody
    List<SysMenu> page(SysMenu sysMenu) {
        return menuService.selectAll(sysMenu);
    }


    /**
     * 新增菜单
     *
     * @param id
     * @param modelAndView
     * @return
     */
    @GetMapping(value = "add/{id}")
    public ModelAndView add(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.setViewName("/system/menu/add");
        modelAndView.addObject("menu", menuService.selectByPrimaryKey(id));
        return modelAndView;
    }

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    @Log(desc = "编辑菜单")
    @PostMapping(value = "save")
    public @ResponseBody
    Result save(SysMenu menu) {
        return menuService.save(menu);
    }

    /**
     * 编辑菜单
     *
     * @param id
     * @param modelAndView
     * @return
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView) {
        SysMenu menu = menuService.selectByPrimaryKey(id);
        modelAndView.addObject("menu", menu);
        modelAndView.setViewName("/system/menu/edit");
        return modelAndView;
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Log(desc = "删除菜单")
    @RequestMapping(value = "delete/{id}")
    public @ResponseBody
    Result delete(@PathVariable String id) {
        menuService.deleteByPrimaryKey(id);
        return Result.OK();
    }
}
