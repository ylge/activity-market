package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.ScanRecord;

public interface ScanRecordMapper extends BaseMapper<ScanRecord, String> {
    ScanRecord getRecordByUserId(Integer userId);
}