package com.geyl.bean.model.system;

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
public class SysMenu implements Serializable {

	private Integer id;
    /**
     * 菜单父编码
     */
	private Integer parentId;
    /**
     * 名称
     */
	private String name;
    /**
     * 请求地址
     */
	private String url;
    /**
     * 是否是菜单
     */
	private Integer isMenu;
    /**
     * 菜单层级
     */
	private Integer level;
    /**
     * 菜单排序
     */
	private Integer sort;
    /**
     * 是否有效 0 无效 1 有效
     */
	private Integer status;
    /**
     * 图标
     */
	private String icon;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 创建人
     */
	private String createBy;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 更新人
     */
	private String updateBy;

    /**
     * 下级菜单
     */
	private List<SysMenu> child;
}
