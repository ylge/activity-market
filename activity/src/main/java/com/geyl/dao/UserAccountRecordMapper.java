package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.UserAccountRecord;
import com.geyl.vo.RewardVO;

import java.util.List;

public interface UserAccountRecordMapper extends BaseMapper<UserAccountRecord,String> {
    List<RewardVO> getActivityUserRed(String goodsId);

    int getGetRewardInfoByOrderNo(String orderNo);
}