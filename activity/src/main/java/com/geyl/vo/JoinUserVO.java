package com.geyl.vo;

import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @date 2019-4-12 15:46
 */
@Data
public class JoinUserVO {
    private String userName;
    private String avatar;
    private String phone;
    private String createTime;
    private Integer status;
}
