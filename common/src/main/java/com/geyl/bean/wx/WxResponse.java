package com.geyl.bean.wx;

import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.bean.wx
 * @date 2019-4-12 9:59
 */
@Data
public class WxResponse {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;
}
