package com.geyl.service;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.model.StoreCooperate;
import com.geyl.bean.model.StoreInfo;
import com.geyl.dao.StoreInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author geyl
 * @date 2019-4-11 10:07
 */
@Service
public class StoreService extends BaseServiceImpl<StoreInfo, String> {

    @Autowired
    private StoreInfoMapper storeInfoMapper;

    @Override
    public BaseMapper<StoreInfo, String> getMappser() {
        return storeInfoMapper;
    }

    public void addStore(StoreCooperate storeCooperate) {
        storeInfoMapper.addStore(storeCooperate);
    }
}
