package com.geyl.service.impl;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.model.system.SysOperationLog;
import com.geyl.dao.SysOperationLogMapper;
import com.geyl.service.SysOperationLogService;
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
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLog, String> implements SysOperationLogService {
	@Autowired
    private SysOperationLogMapper sysOperationLogMapper;

    @Override
    public BaseMapper<SysOperationLog, String> getMappser() {
        return sysOperationLogMapper;
    }
}
