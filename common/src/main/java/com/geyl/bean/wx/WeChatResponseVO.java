package com.geyl.bean.wx;

import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.bean.wx
 * @date 2019-4-11 17:03
 */
@Data
public class WeChatResponseVO {
    private static final long serialVersionUID = 1L;
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packageValue;
    private String partnerId;
    private String prepayId;
    private String sign;
    /**
     * 小程序签名类型 MD5
     */
    private String signType;

    @Override
    public String toString() {
        return "微信返回返回串：appId:" + appId + ",timeStamp:" + timeStamp + ",nonceStr:" + nonceStr + ",packageValue:" + packageValue + ",partnerId:" + partnerId + ",prepayId:" + prepayId + ",sign:" + sign;
    }
}
