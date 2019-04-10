package com.geyl.service;

import com.geyl.bean.ShiroUser;
import com.geyl.bean.model.system.*;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Auther: geyl
 *@Date: 2018/5/8
 *@Description
 */
@Service
public class ShiroFactory {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    public SysUser user(String username) {
        SysUser user = sysUserService.getUserByName(username);
        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        return user;
    }
    public ShiroUser shiroUser(SysUser user) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getUserId());            // 账号id
        shiroUser.setUsername(user.getUsername());// 账号
        shiroUser.setName(user.getName());        // 用户名称
        List<SysUserRole> userRoleList = sysUserRoleService.selectByUserId(user.getUserId());
        List<SysRole> roleList = new ArrayList<>();
        if (userRoleList != null && userRoleList.size() > 0){
            for (SysUserRole sysRoleUser: userRoleList
                 ) {
                SysRole role = sysRoleService.selectByPrimaryKey(sysRoleUser.getRoleId().toString());
                roleList.add(role);
            }
            shiroUser.setRoleList(roleList);
        }
        shiroUser.setCreateTime(user.getCreateTime());

        if (roleList != null && roleList.size() >0 ){
            //权限菜单值
            List<String> permissionValues = new ArrayList<>();
            List<String> roleValues = new ArrayList<>();
            //角色权限菜单
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            for (SysRole r:roleList ) {
                roleValues.add(r.getRoleId().toString());
                List<SysRoleMenu> ll = sysRoleMenuService.selectByRoleId(r.getRoleId());
                sysRoleMenus.addAll(ll);
            }
            shiroUser.setRoleValues(roleValues);

            shiroUser.setPrivilegeList(sysRoleMenus);
            List<String> ids = new ArrayList<>();
            for (SysRoleMenu sysRoleMenu : sysRoleMenus){
                //去重
                if (!ids.contains(sysRoleMenu.getMenuId())){
                    ids.add(sysRoleMenu.getMenuId().toString());
                }
            }
            Map<String,Object> param = new HashMap<>();
            param.put("menuIds", ids);
            List<SysMenu> menuList = sysMenuService.listLevelSysMenu(param);

            for (SysMenu sysMenu:menuList ) {
                permissionValues.add(sysMenu.getUrl());
            }
            shiroUser.setPermissionValues(permissionValues);
        }
        return shiroUser;
    }
    public SimpleAuthenticationInfo buildAuthenticationInfo(ShiroUser shiroUser, SysUser user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }
}
