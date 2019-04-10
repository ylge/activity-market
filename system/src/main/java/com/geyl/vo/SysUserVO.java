package com.geyl.vo;

import com.geyl.bean.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author geyl
 * @Title: SysUserVO
 * @Description: 用户VO类
 * @date 2018-6-1 13:38
 */
@Data
public class SysUserVO extends PageRequest implements Serializable {
    private Integer userId;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String roles;
    private String roleId;
    private String departmentId;
    private String department;
    private String company;
}
