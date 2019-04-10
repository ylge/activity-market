package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysMenu;
import com.geyl.bean.model.system.SysRoleMenu;
import com.geyl.dao.SysRoleMenuMapper;
import com.geyl.exception.MyException;
import com.geyl.service.SysMenuService;
import com.geyl.service.SysRoleMenuService;
import com.geyl.util.MenuTreeUtil;
import com.geyl.vo.RoleMenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu, String> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public BaseMapper<SysRoleMenu, String> getMappser() {
        return sysRoleMenuMapper;
    }

    @Override
    public List<SysRoleMenu> selectByRoleId(Integer roleId) {
        return sysRoleMenuMapper.selectByRoleId(roleId);
    }

    @Override
    public Result getMenuByRoleId(String roleId) {
        List<SysMenu> menuLists = sysMenuService.selectAll(null);
        List<SysRoleMenu> roleMenuLists = roleId==null?new ArrayList<>():sysRoleMenuMapper.selectByRoleId(Integer.valueOf(roleId));
        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuLists, roleMenuLists);
        List<Map<String, Object>> mapList = menuTreeUtil.buildTree();
        return Result.OK(mapList);
    }

    @Override
    public Result grant(RoleMenuVO roleMenuVO) throws MyException {
        if(roleMenuVO.getMenuIds().length>0){
            this.deleteByPrimaryKey(roleMenuVO.getRoleId());
            if (roleMenuVO.getMenuIds() != null && StringUtils.isNotEmpty(roleMenuVO.getRoleId())) {
                if (roleMenuVO.getMenuIds().length>0) {
                    Arrays.stream(roleMenuVO.getMenuIds()).forEach(menuId -> {
                        SysRoleMenu sysRoleMenu = new SysRoleMenu();
                        sysRoleMenu.setRoleId(Integer.parseInt(roleMenuVO.getRoleId()));
                        sysRoleMenu.setMenuId(Integer.parseInt(menuId));
                        this.insertSelective(sysRoleMenu);
                    });
                }
            }
        }else{
            throw new MyException("请给角色配置菜单");
        }
        return Result.OK();
    }
}
