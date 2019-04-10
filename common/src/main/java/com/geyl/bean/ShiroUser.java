package com.geyl.bean;


import com.geyl.bean.model.system.SysRole;
import com.geyl.bean.model.system.SysRoleMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
　* @author geyl
　* @date 2018-5-23 13:38
　*/
public class ShiroUser implements Serializable{
    private static final long serialVersionUID = -4661753370573516137L;

    private Integer id;          // 主键ID
    private String username;      // 账号
    private String name;         // 姓名
    private Integer deptId;      // 部门id
    private String deptName;        // 部门名称
    private Date createTime;
    //角色集
    private List<SysRole> roleList;

    List<SysRoleMenu> privilegeList;
    //菜单权限值
    List<String> permissionValues = new ArrayList<>();
    //角色值
    List<String> roleValues = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public List<SysRoleMenu> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<SysRoleMenu> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public List<String> getPermissionValues() {
        return permissionValues;
    }

    public void setPermissionValues(List<String> permissionValues) {
        this.permissionValues = permissionValues;
    }

    public List<String> getRoleValues() {
        return roleValues;
    }

    public void setRoleValues(List<String> roleValues) {
        this.roleValues = roleValues;
    }
}
