package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.UserAccountRecord;
import com.geyl.vo.UserRedVO;

import java.util.List;

public interface UserAccountRecordMapper extends BaseMapper<UserAccountRecord,String> {
    List<UserRedVO> getActivityUserRed(String goodsId);

}