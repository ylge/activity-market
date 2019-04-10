package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.Result;
import com.geyl.bean.model.system.SysCompany;
import com.geyl.dao.SysCompanyMapper;
import com.geyl.service.SysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  公司服务实现类
 * </p>
 *
 * @author geyongliang
 * @since 2018-05-11
 */
@Service
public class SysCompanyServiceImpl extends BaseServiceImpl<SysCompany, String> implements SysCompanyService {

    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    @Override
    public BaseMapper<SysCompany, String> getMappser() {
        return sysCompanyMapper;
    }

    @Override
    public Result save(SysCompany sysCompany) {
        if(sysCompany.getCompanyId()==null){
            this.insertSelective(sysCompany);
        }else{
            this.updateByPrimaryKeySelective(sysCompany);
        }
        return Result.OK();
    }
}
