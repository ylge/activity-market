package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.OrderInfo;
import com.geyl.vo.OrderInfoVO;

public interface OrderInfoMapper extends BaseMapper<OrderInfo,String> {
    OrderInfoVO getOrderDetailByNo(String orderNo);

    void updateOrderStatusByOrderNo(OrderInfo orderInfo);
}