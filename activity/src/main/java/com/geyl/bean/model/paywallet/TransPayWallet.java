package com.geyl.bean.model.paywallet;

import lombok.Data;

/**
 * 企业付款零钱
 *
 * @author yangqisheng
 */
@Data
public class TransPayWallet {

    /**
     * 商户账号appid
     *
     * 申请商户号的appid或商户号绑定的appid
     */
    private String mch_appid;
    /**
     * 商户订单号
     */
    private String partner_trade_no;
    /**
     * 用户openid
     */
    private String openid;
    /**
     * 是否校验用户姓名
     */
    private boolean check_name;
    /**
     * 收款用户姓名
     */
    private String re_user_name;
    /**
     * 付款金额：RMB分
     */
    private int amount;
    /**
     * 企业付款备注
     */
    private String desc;
    /**
     * Ip地址
     *
     * 该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。
     */
    private String spbill_create_ip;

}
