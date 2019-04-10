package com.geyl.vo;

import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @Description: 修改用户密码
 * @date 2018-12-4 20:26
 */
@Data
public class UserPwdVO {
    private String userId;
    private String oldPassword;
    private String newPassword;
}
