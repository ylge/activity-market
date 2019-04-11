package com.geyl.bean.model;

import com.geyl.bean.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderInfo extends PageRequest implements Serializable {
    private Integer orderId;

    private String orderNo;

    private Integer storeId;

    private Integer pUserId;

    private Integer userId;

    private Integer goodsId;

    private String payAccount;

    private Integer buyCount;

    private BigDecimal paymentAmount;

    private Boolean payType;

    private BigDecimal orderAmount;

    private Boolean status;

    private String orderCode;

    private Date createTime;

    private Date payTime;

    private Date updateTime;

    private String remark1;

    private String remark2;

    private String remark3;
}