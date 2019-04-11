package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.ActivityGoods;
import com.geyl.vo.ActivityGoodsVO;

import java.util.List;

public interface ActivityGoodsMapper extends BaseMapper<ActivityGoods,String> {
    List<ActivityGoodsVO> getAcitvityGoodsList(ActivityGoodsVO activityGoodsVO);

    ActivityGoodsVO getGoodsDetail(String goodsId);
}