package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysRole;
import com.geyl.dao.SysRoleMapper;
import com.geyl.exception.MyException;
import com.geyl.service.SysRoleMenuService;
import com.geyl.service.SysRoleService;
import com.geyl.service.SysUserService;
import com.geyl.vo.RoleMenuVO;
import com.geyl.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, String> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public BaseMapper<SysRole, String> getMappser() {
        return sysRoleMapper;
    }


    @Override
    public Result save(RoleMenuVO roleMenuVO) throws MyException {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleMenuVO.getRoleId() == null ? null : Integer.parseInt(roleMenuVO.getRoleId()));
        BeanUtils.copyProperties(roleMenuVO, sysRole);
        if (sysRole.getRoleId() == null) {
            this.insertSelective(sysRole);
            roleMenuVO.setRoleId(sysRole.getRoleId().toString());
        } else {
            this.updateByPrimaryKeySelective(sysRole);
        }
        sysRoleMenuService.grant(roleMenuVO);
        return Result.OK();
    }

    @Override
    public PageResult<SysUserVO> userList(SysUserVO sysUserVO,String type) {
        PageResult<SysUserVO> sysUserVOS = new PageResult<>();
        if ("1".equals(type)) {
            sysUserVOS = sysUserService.selectByRoleId(sysUserVO);
        } else if ("2".equals(type)) {
            sysUserVOS = sysUserService.selectUserOutRoleId(sysUserVO);
        }
        return sysUserVOS;
    }
}
