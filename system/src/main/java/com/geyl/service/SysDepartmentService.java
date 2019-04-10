package com.geyl.service;


import com.geyl.base.BaseService;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysDepartment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysDepartmentService extends BaseService<SysDepartment,String> {

    Result saveDepartment(SysDepartment sysDepartment);
}
