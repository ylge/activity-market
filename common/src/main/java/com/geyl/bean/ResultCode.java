package com.geyl.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author geyl
 * @date 2018-5-23 16:02
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCode {
    OK(200,"操作成功"),
    FAIL(-1,"操作失败");
    public Integer code;
    public String msg;
}
