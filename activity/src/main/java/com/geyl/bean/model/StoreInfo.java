package com.geyl.bean.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class StoreInfo implements Serializable {
    private String storeId;

    private String storeName;

    private String storeImage;

    private String storeAddress;

    private String storePhone;

    private String linkName;

    private Boolean status;

    private String storeCode;

    private String remark1;

    private String remark2;

    private String remark3;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

}