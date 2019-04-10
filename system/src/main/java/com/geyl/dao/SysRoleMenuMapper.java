package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.system.SysRoleMenu;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu,String> {

    List<SysRoleMenu> selectByRoleId(Integer roleId);
}