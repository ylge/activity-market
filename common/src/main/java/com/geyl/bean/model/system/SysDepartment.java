package com.geyl.bean.model.system;

import com.geyl.bean.PageRequest;
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
public class SysDepartment extends PageRequest implements Serializable{

    /**
     * id
     */
	private Integer departmentId;
    /**
     * 公司代码
     */
	private Integer companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
    /**
     * 部门编号
     */
	private String code;
    /**
     * 部门名称
     */
	private String name;
    /**
     * 上级代码
     */
	private String parentCode;
    /**
     * 部门描述
     */
	private String comment;
    /**
     * 是否有效 0 无效 1 有效
     */
	private Integer status;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

}
