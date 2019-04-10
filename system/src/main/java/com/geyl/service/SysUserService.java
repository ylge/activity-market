package com.geyl.service;

import com.geyl.base.BaseService;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysUser;
import com.geyl.exception.MyException;
import com.geyl.vo.SysUserVO;
import com.geyl.vo.UserPwdVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysUserService extends BaseService<SysUser,String > {

    SysUser getUserByName(String username);

    Result deleteUser(String id);

    Result saveUser(SysUserVO sysUserVO);

    Result updatePwd(UserPwdVO userPwdVO) throws MyException;

    PageResult<SysUserVO> selectUserOutRoleId(SysUserVO sysUserVO);

    PageResult<SysUserVO> selectByRoleId(SysUserVO sysUserVO);
}
