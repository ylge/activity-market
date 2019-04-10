package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysUserRole;
import com.geyl.dao.SysUserRoleMapper;
import com.geyl.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole, String> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public BaseMapper<SysUserRole, String> getMappser() {
        return sysUserRoleMapper;
    }

    @Override
    public List<SysUserRole> selectByUserId(Integer userId) {
        return sysUserRoleMapper.selectByUserId(userId);
    }

    @Override
    public void deleteByUserId(String userId) {
        sysUserRoleMapper.deleteByUserId(userId);
    }

    @Override
    public Result deleteRoleByUserId(Integer roleId, Integer userId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        sysUserRoleMapper.deleteRoleByUserId(sysUserRole);
        return Result.OK();
    }

    @Override
    public Result addRoleByUserId(Integer roleId, Integer userId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        sysUserRoleMapper.insertSelective(sysUserRole);
        return Result.OK();
    }
}
