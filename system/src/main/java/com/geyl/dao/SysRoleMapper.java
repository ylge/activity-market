package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.system.SysRole;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysRoleMapper extends BaseMapper<SysRole,String> {

    List<SysRole> selectAllRole();
}