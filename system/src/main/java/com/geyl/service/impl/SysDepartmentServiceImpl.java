package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysDepartment;
import com.geyl.dao.SysDepartmentMapper;
import com.geyl.service.SysCompanyService;
import com.geyl.service.SysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Service
public class SysDepartmentServiceImpl extends BaseServiceImpl<SysDepartment, String> implements SysDepartmentService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private SysCompanyService companyService;

    @Override
    public BaseMapper<SysDepartment, String> getMappser() {
        return sysDepartmentMapper;
    }

    @Override
    public Result saveDepartment(SysDepartment sysDepartment) {
        sysDepartment.setCompanyName(companyService.selectByPrimaryKey(sysDepartment.getCompanyId().toString()).getShortName());
        if (sysDepartment.getDepartmentId() == null) {
            super.insertSelective(sysDepartment);
        } else {
            super.updateByPrimaryKeySelective(sysDepartment);
        }
        return Result.OK();
    }

}
