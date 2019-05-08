package com.geyl.bean.model;

import com.geyl.bean.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StoreCooperate extends PageRequest implements Serializable {
    private String id;

    private String userName;

    private String phone;

    private Date createTime;


}