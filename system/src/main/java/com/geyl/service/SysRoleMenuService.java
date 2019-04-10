package com.geyl.service;

import com.geyl.base.BaseService;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysRoleMenu;
import com.geyl.exception.MyException;
import com.geyl.vo.RoleMenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu,String> {

    List<SysRoleMenu> selectByRoleId(Integer roleId);

    Result getMenuByRoleId(String roleId);

    Result grant(RoleMenuVO roleMenuVO) throws MyException;
}
