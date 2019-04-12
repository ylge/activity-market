package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.ScanRecord;
import com.geyl.vo.ScanUserVO;

import java.util.List;

public interface ScanRecordMapper extends BaseMapper<ScanRecord, String> {
    ScanRecord getRecordByUserId(Integer userId);

    List<ScanUserVO> getActivityUser(String goodsId);
}