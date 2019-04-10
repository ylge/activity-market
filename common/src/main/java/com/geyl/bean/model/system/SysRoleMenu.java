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
public class SysRoleMenu implements Serializable{

	private Integer roleId;
	private Integer menuId;
	private Date createTime;

}
