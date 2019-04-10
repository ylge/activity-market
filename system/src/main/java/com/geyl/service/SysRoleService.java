package com.geyl.service;

import com.geyl.base.BaseService;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysRole;
import com.geyl.exception.MyException;
import com.geyl.vo.RoleMenuVO;
import com.geyl.vo.SysUserVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysRoleService extends BaseService<SysRole, String> {

    Result save(RoleMenuVO sysrole) throws MyException;

    PageResult<SysUserVO> userList(SysUserVO sysUserVO, String type);
}
