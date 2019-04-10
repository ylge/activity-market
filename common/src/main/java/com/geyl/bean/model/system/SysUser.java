package com.geyl.bean.model.system;

import com.geyl.bean.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Data
public class SysUser extends PageRequest implements Serializable{

	private Integer userId;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 账号
     */
	private String username;
    /**
     * 密码
     */
	private String password;
    /**
     * 用户姓名
     */
	private String name;
    /**
     * 商家用户id
     */
	private String merchantId;
    /**
     * 盐
     */
	private String salt;
    /**
     * 手机号码
     */
	private String phone;
	/**
     * 是否有效 0 无效 1 有效
     */
	private Integer status;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
     * 创建人
     */
	private String createBy;
	/**
     * 更新日期
     */
	private Date updateTime;
    /**
     * 更新人
     */
	private String updateBy;

	private List<SysRole> roleList;

}
