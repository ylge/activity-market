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
public class SysOperationLog implements Serializable {

	private Long id;
    /**
     * 操作人ID
     */
	private Integer userId;
    /**
     * 操作人姓名
     */
	private String userName;
    /**
     * 用户操作
     */
	private String operation;
    /**
     * 方法
     */
	private String method;
    /**
     * 参数
     */
	private String args;

	private Date createTime;

}
