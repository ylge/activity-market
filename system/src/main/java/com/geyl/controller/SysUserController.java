package com.geyl.controller;

import com.geyl.annotation.Log;
import com.geyl.base.BaseController;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysUser;
import com.geyl.exception.MyException;
import com.geyl.service.SysDepartmentService;
import com.geyl.service.SysRoleService;
import com.geyl.service.SysUserService;
import com.geyl.vo.SysUserVO;
import com.geyl.vo.UserPwdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author geyl
 * @Title: SysUserController
 * @Package com.geyl.controller
 * @Description: 用户管理
 * @date 2018-5-18 13:40
 */
@Controller
@RequestMapping(value = "system/user/")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysDepartmentService sysDepartmentService;

    @GetMapping(value = "list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("system/user/list");
        return modelAndView;
    }

    @GetMapping(value = "page")
    public @ResponseBody
    PageResult<SysUser> page(SysUser user) {
        return sysUserService.pageList(user);
    }

    /**
     * 　* @description:添加用户
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @GetMapping(value = "add")
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.setViewName("/system/user/add");
        modelAndView.addObject("roles", sysRoleService.selectAll(null));
        modelAndView.addObject("departments", sysDepartmentService.selectAll(null));
        return modelAndView;
    }

    /**
     * 　* @description:新增用户
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @Log(desc = "新增用户")
    @PostMapping(value = "add")
    public @ResponseBody
    Result addUser(SysUserVO sysUserVO) {
        return sysUserService.saveUser(sysUserVO);
    }

    /**
     * 　* @description:添加用户
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @GetMapping(value = "edit/{userId}")
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable String userId) {
        modelAndView.setViewName("/system/user/edit");
        modelAndView.addObject("sysUser", sysUserService.selectByPrimaryKey(userId));
        return modelAndView;
    }

    /**
     * 　* @description:修改用户
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @Log(desc = "修改用户")
    @PostMapping(value = "update")
    public @ResponseBody
    Result update(SysUserVO sysUserVO) {
        return sysUserService.saveUser(sysUserVO);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Log(desc = "删除用户")
    @GetMapping(value = "delete/{id}")
    public @ResponseBody
    Result delete(@PathVariable String id) {
        return sysUserService.deleteUser(id);
    }

    /**
     * 　* @description:密码修改
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @GetMapping(value = "updatePassword/{userId}")
    public ModelAndView updatePassword(ModelAndView modelAndView, @PathVariable String userId) {
        modelAndView.setViewName("/system/user/pwd_update");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping(value = "updatePassword")
    @ResponseBody
    public Result updatePwd(UserPwdVO userPwdVO) throws MyException {
        return sysUserService.updatePwd(userPwdVO);
    }

}
