package com.geyl.bean.wx;

import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.bean.wx
 * @date 2019-4-12 9:59
 */
@Data
public class WxUserResponse {
    private String openid;
    private String nickname;
    private String sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private String[] privilege;
}
