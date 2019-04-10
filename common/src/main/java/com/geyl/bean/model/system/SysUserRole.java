package com.geyl.bean.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Data
public class SysUserRole implements Serializable {

	private Integer id;
	private Integer userId;
	private Integer roleId;
	private Date createTime;
	private String createBy;
}
