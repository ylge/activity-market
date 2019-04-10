package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.system.SysUser;
import com.geyl.vo.SysUserVO;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysUserMapper extends BaseMapper<SysUser,String> {

    SysUser getUserByName(String username);

    List<SysUserVO> selectUserOutRoleId(SysUserVO roleId);

    List<SysUserVO> selectByRoleId(SysUserVO roleId);
}