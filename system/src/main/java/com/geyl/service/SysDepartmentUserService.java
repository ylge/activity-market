package com.geyl.service;

import com.geyl.base.BaseService;
import com.geyl.bean.model.system.SysDepartmentUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysDepartmentUserService extends BaseService<SysDepartmentUser,String> {

    void deleteByUserId(String userId);
}
