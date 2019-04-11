package com.geyl.bean.model;

import com.geyl.bean.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class ActivityGoods extends PageRequest implements Serializable {
    private String goodsId;

    private String goodsName;

    private String goodsImage;

    private String goodsDetail;

    private BigDecimal originalPrice;

    private BigDecimal goodsPrice;

    private Integer purchaseLimit;

    private Integer inventory;

    private String activityMusic;

    private String backgroundImage;

    private Integer joinNumber;

    private Integer scanNumber;

    private String beginTime;

    private String endTime;

    private Integer status;

    private String storeId;

    private String remark1;

    private String remark2;

    private String remark3;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String activityRule;

}