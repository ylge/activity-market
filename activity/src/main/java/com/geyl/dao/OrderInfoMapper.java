package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.OrderInfo;
import com.geyl.vo.*;

import java.util.List;
import java.util.Map;

public interface OrderInfoMapper extends BaseMapper<OrderInfo,String> {
    OrderInfoVO getOrderDetailByNo(String orderNo);

    int updateOrderStatusByOrderNo(OrderInfo orderInfo);

    List<JoinUserVO> getJoinUser(String goodsId);

    Integer checkIsOrder(OrderInfoVO orderInfoVO);

    ActivityManageVO getActivityData(String goodsId);

    List<OrderInfoVO> getOrderList(String goodsId);

    List<StoreUserVO> getStoreUser(Map<String, Object> param);

    List<IncomeVO> getIncomeListByGoodsId(String goodsId);

    List<RewardVO> getWithdrawListByGoodsId(String goodsId);
}