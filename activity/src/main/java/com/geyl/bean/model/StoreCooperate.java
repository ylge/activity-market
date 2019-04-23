package com.geyl.bean.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StoreCooperate implements Serializable {
    private String id;

    private String userName;

    private String phone;

    private Date createTime;


}