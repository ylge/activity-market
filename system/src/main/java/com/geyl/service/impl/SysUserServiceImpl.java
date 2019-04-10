package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysDepartmentUser;
import com.geyl.bean.model.system.SysUser;
import com.geyl.bean.model.system.SysUserRole;
import com.geyl.dao.SysUserMapper;
import com.geyl.exception.MyException;
import com.geyl.service.SysDepartmentUserService;
import com.geyl.service.SysUserRoleService;
import com.geyl.service.SysUserService;
import com.geyl.shiro.ShiroKit;
import com.geyl.vo.SysUserVO;
import com.geyl.vo.UserPwdVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, String> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDepartmentUserService departmentUserService;

    @Override
    public BaseMapper<SysUser, String> getMappser() {
        return sysUserMapper;
    }

    @Override
    public SysUser getUserByName(String username) {
        return sysUserMapper.getUserByName(username);
    }

    @Override
    public Result deleteUser(String userId) {
        //删除用户
//        super.deleteByPrimaryKey(userId);
        SysUser sysUser = new SysUser();
        sysUser.setUserId(Integer.parseInt(userId));
        sysUser.setStatus(0);
        this.updateByPrimaryKeySelective(sysUser);
        //删除用户角色
        sysUserRoleService.deleteByUserId(userId);
        //删除用户部门
        departmentUserService.deleteByUserId(userId);
        return Result.OK();
    }

    @Override
    public Result saveUser(SysUserVO sysUserVO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVO, sysUser);
        String salt = ShiroKit.getRandomSalt(5);
        sysUser.setSalt(salt);
        String saltPwd = ShiroKit.md5(sysUser.getPassword(), salt);
        sysUser.setPassword(saltPwd);
        if (ObjectUtils.isEmpty(sysUserVO.getUserId())) {
            SysUser user = sysUserMapper.getUserByName(sysUserVO.getUsername());
            if (user != null) {
                return Result.Fail("该账号已被使用");
            }
            super.insertSelective(sysUser);
        } else {
            super.updateByPrimaryKeySelective(sysUser);
        }
        //增加角色
        if (!ObjectUtils.isEmpty(sysUserVO.getRoles())) {
            String[] roles = sysUserVO.getRoles().split(",");
            Arrays.stream(roles).forEach(s -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(Integer.valueOf(s));
                sysUserRole.setUserId(sysUser.getUserId());
                sysUserRoleService.insertSelective(sysUserRole);
            });
        }
        //增加部门
        if (!ObjectUtils.isEmpty(sysUserVO.getDepartmentId())) {
            SysDepartmentUser sysDepartmentUser = new SysDepartmentUser();
            sysDepartmentUser.setUserId(sysUser.getUserId());
            sysDepartmentUser.setDepartmentId(Integer.parseInt(sysUserVO.getDepartmentId()));
            departmentUserService.insertSelective(sysDepartmentUser);
        }
        return Result.OK();
    }

    @Override
    public Result updatePwd(UserPwdVO userPwdVO) throws MyException {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userPwdVO.getUserId());
        if (sysUser != null) {
            String salt = sysUser.getSalt();
            String oldPwd = ShiroKit.md5(userPwdVO.getOldPassword(), salt);
            if (sysUser.getPassword().equals(oldPwd)) {
                String newPwd = ShiroKit.md5(userPwdVO.getNewPassword(), salt);
                sysUser.setPassword(newPwd);
            } else {
                throw new MyException("原密码不正确");
            }
        } else {
            throw new MyException("用户信息有误");
        }
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return Result.OK();
    }

    @Override
    public PageResult<SysUserVO> selectUserOutRoleId(SysUserVO sysUserVO) {
        Integer offset = sysUserVO.getOffset();
        Integer limit = sysUserVO.getLimit();
        PageHelper.startPage(offset / limit + 1, limit);
        List<SysUserVO> tList = sysUserMapper.selectUserOutRoleId(sysUserVO);
        PageResult<SysUserVO> result = new PageResult<>(new PageInfo<>(tList));
        return result;
    }

    @Override
    public PageResult<SysUserVO> selectByRoleId(SysUserVO sysUserVO) {
        Integer offset = sysUserVO.getOffset();
        Integer limit = sysUserVO.getLimit();
        PageHelper.startPage(offset / limit + 1, limit);
        List<SysUserVO> tList = sysUserMapper.selectByRoleId(sysUserVO);
        PageResult<SysUserVO> result = new PageResult<>(new PageInfo<>(tList));
        return result;
    }

}
