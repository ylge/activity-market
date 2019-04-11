package com.geyl.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @date 2019-4-11 18:11
 */
@Data
public class OrderAdd implements Serializable {
    private String goodsId;
    private String phone;
    private String userId;
    private String userName;
    private String openid;
    private String pUserId;
}
