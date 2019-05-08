package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.StoreCooperate;
import com.geyl.bean.model.StoreInfo;

import java.util.List;

public interface StoreInfoMapper extends BaseMapper<StoreInfo,String> {
    void addStore(StoreCooperate storeCooperate);

    List<StoreCooperate> getcoopertePageList(StoreCooperate storeCooperate);
}