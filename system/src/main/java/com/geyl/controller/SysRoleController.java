package com.geyl.controller;

import com.geyl.annotation.Log;
import com.geyl.base.BaseController;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysRole;
import com.geyl.exception.MyException;
import com.geyl.service.SysRoleMenuService;
import com.geyl.service.SysRoleService;
import com.geyl.service.SysUserRoleService;
import com.geyl.vo.RoleMenuVO;
import com.geyl.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author geyl
 * @Title: SysRoleController
 * @Package com.geyl.controller
 * @Description: 角色管理
 * @date 2018-5-18 13:40
 */
@Controller
@RequestMapping(value = "system/role/")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 　* @description:角色列表
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:34
     *
     */
    @GetMapping(value = "list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/role/list");
        return modelAndView;
    }

    @GetMapping(value = "page")
    public @ResponseBody
    PageResult<SysRole> page(SysRole sysRole) {
        return sysRoleService.pageList(sysRole);
    }

    /**
     * 　* @description:角色新增
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @GetMapping(value = "add")
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/role/add");
        return modelAndView;
    }

    /**
     * 　* @description: 角色菜单查询
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-6-8 16:39
     *
     */
    @GetMapping(value = "menutree")
    @ResponseBody
    public Result menutree(@RequestParam(value = "roleId") String roleId) {
        return sysRoleMenuService.getMenuByRoleId(roleId);
    }

    /**
     * 角色授权页面跳转
     *
     * @return
     */
    @GetMapping(value = "grant")
    public ModelAndView grant(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/role/grant");
        modelAndView.addObject("roles", sysRoleService.selectAll(null));
        return modelAndView;
    }

    /**
     * 　* @description:角色编辑
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @GetMapping(value = "edit/{roleId}")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable String roleId) {
        modelAndView.setViewName("/system/role/edit");
        modelAndView.addObject("role", sysRoleService.selectByPrimaryKey(roleId));
        return modelAndView;
    }

    /**
     * 　* @description:新增角色
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @Log(desc = "角色编辑")
    @PostMapping(value = "add")
    public @ResponseBody
    Result save(RoleMenuVO sysrole) throws MyException {
        return sysRoleService.save(sysrole);
    }

    /**
     * 　* @description:编辑角色
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @Log(desc = "角色编辑")
    @PostMapping(value = "update")
    public @ResponseBody
    Result update(RoleMenuVO sysrole) throws MyException {
        return sysRoleService.save(sysrole);
    }

    /**
     * 　* @description:角色用户编辑
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @GetMapping(value = "user/{roleId}")
    public ModelAndView editUser(ModelAndView modelAndView, @PathVariable String roleId) {
        modelAndView.setViewName("/system/role/role_user");
        modelAndView.addObject("roleId",roleId);
        return modelAndView;
    }

    /**
     * 角色用户查询
     * @param sysUserVO
     * @param type
     * @return
     */
    @GetMapping(value = "user")
    public @ResponseBody
    PageResult<SysUserVO> userList(SysUserVO sysUserVO
            , @RequestParam("type") String type) {
        return sysRoleService.userList(sysUserVO, type);
    }

    /**
     * 　* @description:移除角色用户
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @GetMapping(value = "/user/delete/{roleId}/{userId}")
    public @ResponseBody
    Result deleteStore(@PathVariable Integer roleId, @PathVariable Integer userId) {
        return sysUserRoleService.deleteRoleByUserId(roleId,userId);
    }

    /**
     * 　* @description:添加角色用户
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @GetMapping(value = "/user/add/{roleId}/{userId}")
    public @ResponseBody
    Result addStore(@PathVariable Integer roleId,@PathVariable Integer userId) {
        return sysUserRoleService.addRoleByUserId(roleId,userId);
    }
}
