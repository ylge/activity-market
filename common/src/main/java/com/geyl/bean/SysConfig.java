package com.geyl.bean;

import lombok.Data;

@Data
public class SysConfig extends PageRequest {
    private Integer id;

    private String keyName;

    private String keyValue;

    private String remark;

}