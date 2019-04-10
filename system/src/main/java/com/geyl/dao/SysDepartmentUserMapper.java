package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.system.SysDepartmentUser;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
public interface SysDepartmentUserMapper extends BaseMapper<SysDepartmentUser,String> {

    void deleteByUserId(String userId);
}