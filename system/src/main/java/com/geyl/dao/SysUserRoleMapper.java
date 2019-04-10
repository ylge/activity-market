package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.system.SysUserRole;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole,String> {

    List<SysUserRole> selectByUserId(Integer userId);

    void deleteByUserId(String userId);

    void deleteRoleByUserId(SysUserRole sysUserRole);
}