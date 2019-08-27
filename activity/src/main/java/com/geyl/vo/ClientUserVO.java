package com.geyl.vo;

import lombok.Data;

/**
 * @author geyl
 * @Title: ${CLASS_NAME}
 * @Package com.geyl.vo
 * @date 2019-4-19 18:14
 */
@Data
public class ClientUserVO {
    private Integer userId;
    private String openid;
    private Integer isOrder;
    private Integer isManager;
    private String orderCode;
}
