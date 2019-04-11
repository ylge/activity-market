package com.geyl.bean.model;

import com.geyl.bean.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderInfo extends PageRequest implements Serializable {
    private String orderId;

    private String orderNo;

    private String storeId;

    private String pUserId;

    private String  userId;

    private String goodsId;

    private String payAccount;

    private Integer buyCount;

    private BigDecimal paymentAmount;

    private Integer payType;

    private BigDecimal orderAmount;

    private Integer status;

    private String orderCode;

    private Date createTime;

    private Date payTime;

    private Date updateTime;

    private String remark1;

    private String remark2;

    private String remark3;
}