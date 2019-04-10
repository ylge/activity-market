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
public class SysCompany extends PageRequest implements Serializable {


	private Integer companyId;
    /**
     * 公司代码
     */
	private String code;
    /**
     * 公司名称
     */
	private String name;
    /**
     * 公司简称
     */
	private String shortName;
    /**
     * 上级代码
     */
	private String parentCode;
    /**
     * 公司LOGO
     */
	private String logo;
    /**
     * 公司介绍
     */
	private String comment;
    /**
     * 公司地址
     */
	private String address;
    /**
     * 城市代码
     */
	private String cityCode;
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
	private String  createBy;
    /**
     * 更新日期
     */
	private Date updateTime;
    /**
     * 更新人
     */
	private String updateBy;
}
