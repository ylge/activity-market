package com.geyl.service;

import com.geyl.base.BaseService;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author geyongliang
 * @since 2018-04-27
 */
public interface SysMenuService extends BaseService<SysMenu,String> {

    List<SysMenu> getMenu(ArrayList<SysMenu> menuLists, Object obj);

    List<SysMenu> listLevelSysMenu(Map<String, Object> param);

    Result save(SysMenu menu);

}
