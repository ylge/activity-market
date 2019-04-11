package com.geyl.bean.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ClientUser implements Serializable {
    private Integer userId;

    private String openid;

    private String userName;

    private String phone;

    private String avatar;

    private String nickName;

    private String username;

    private String password;

    private String salt;

    private Integer status;

    private Integer gender;

    private Integer age;

    private Date createTime;

    private Date lastLoginTime;

}