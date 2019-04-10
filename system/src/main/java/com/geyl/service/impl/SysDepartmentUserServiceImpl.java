package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.model.system.SysDepartmentUser;
import com.geyl.dao.SysDepartmentUserMapper;
import com.geyl.service.SysDepartmentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Service
public class SysDepartmentUserServiceImpl extends BaseServiceImpl<SysDepartmentUser, String> implements SysDepartmentUserService {

    @Autowired
    private SysDepartmentUserMapper sysDepartmentUserMapper;
    @Override
    public BaseMapper<SysDepartmentUser, String> getMappser() {
        return sysDepartmentUserMapper;
    }

    @Override
    public void deleteByUserId(String userId) {
        sysDepartmentUserMapper.deleteByUserId(userId);
    }
}
