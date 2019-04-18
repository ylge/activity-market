package com.geyl.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @Description: 收入统计
 * @date 2019-4-18 14:17
 */
@Data
public class IncomeVO {
    @Excel(name = "商家")
    private String storeName;
    @Excel(name = "活动商品")
    private String goodsName;
    @Excel(name = "订单金额")
    private String paymentAmount;
    @Excel(name = "购买用户")
    private String userName;
    @Excel(name = "微信昵称")
    private String nickName;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "订单状态", replace = {"待支付_1", "已支付_2", "已核销_3"})
    private String status;
    private String userId;
    @Excel(name = "下单日期")
    private String createTime;
    @Excel(name = "核销码")
    private String orderNo;
}
