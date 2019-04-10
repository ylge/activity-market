package com.geyl.service;

import com.geyl.base.BaseService;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysUserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysUserRoleService extends BaseService<SysUserRole,String> {

    List<SysUserRole> selectByUserId(Integer userId);

    void deleteByUserId(String userId);

    Result deleteRoleByUserId(Integer roleId, Integer userId);

    Result addRoleByUserId(Integer roleId, Integer userId);
}
