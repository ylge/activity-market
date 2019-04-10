package com.geyl.controller;

import com.geyl.bean.ShiroUser;
import com.geyl.bean.model.system.SysMenu;
import com.geyl.bean.model.system.SysRoleMenu;
import com.geyl.service.SysMenuService;
import com.geyl.util.MenuComparator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Auther: geyl
 *@Date: 2018/5/8
 *@Description
 */
@RestController
public class IndexController {
    @Autowired
    private SysMenuService menuService;

    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView){
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        modelAndView.setViewName("/index");
        List<SysRoleMenu> privilegeList = user.getPrivilegeList();
        if (null != privilegeList){
            List<String> ids = new ArrayList<>();
            for (SysRoleMenu p : privilegeList){
                if (!ids.contains(p.getMenuId())){
                    ids.add(p.getMenuId().toString());
                }
            }
            Map<String,Object> param = new HashMap<>();
            param.put("menuIds", ids);
            param.put("level", "1");
            //得到一级菜单
            List<SysMenu> menuList = menuService.listLevelSysMenu(param);
            if (menuList != null){
                param.clear();
                //得到二级菜单
                for (SysMenu menu:menuList) {
                    param.put("menuIds", ids);
                    param.put("parentId", menu.getId());
                    List<SysMenu> secondMenuList = menuService.listLevelSysMenu(param);
                    //得到三级菜单
                    for (SysMenu sysMenu:secondMenuList){
                        param.put("menuIds", ids);
                        param.put("parentId", sysMenu.getId());
                        param.put("isMenu", 1);
                        List<SysMenu> thirdMenuList = menuService.listLevelSysMenu(param);
                        if(thirdMenuList!=null && thirdMenuList.size()>0){
                            thirdMenuList.sort(new MenuComparator());
                            sysMenu.setChild(thirdMenuList);
                        }
                    }
                    secondMenuList.sort(new MenuComparator());
                    menu.setChild(secondMenuList);
                }
                menuList.sort(new MenuComparator());
                modelAndView.addObject("menuList",menuList);
            }
        }
        return modelAndView;
    }
}
