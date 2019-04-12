package com.geyl.bean.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class UserAccountRecord implements Serializable {
    private Long id;

    private String account;

    private String userId;

    private Integer type;

    private BigDecimal amount;

    private BigDecimal balance;

    private String tradeNo;

    private String desc;

    private Date createTime;

    /**
     * 商品id
     */
    private String remark1;

    /**
     * 下级用户
     */
    private String remark2;

    private String remark3;

}