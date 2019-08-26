package com.wxpay;

import org.apache.http.client.HttpClient;

/**
 * 常量
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    public static final String MICROPAY_URL_SUFFIX     = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX   = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX      = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX   = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX       = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX  = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX       = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX     = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX     = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX   = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX      = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX   = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX       = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX  = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX       = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX     = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
    public static final String SANDBOX_SENDREDPACK_URL_SUFFIX  = "/sandboxnew/mmpaymkttransfers/sendredpack";
    public static final String SANDBOX_GETHBINFO_URL_SUFFIX    = "/sandboxnew/mmpaymkttransfers/promotion/gethbinfo";

    /**
     * 作用：商户平台-现金红包-查询红包记录<br>
     * 场景：用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
     * 其他：需要证书
     */
    public static final String GETHBINFO_URL_SUFFIX = "/mmpaymkttransfers/gethbinfo";

    /**
     * 公众号、小程序appid
     */
    public static String APP_ID = "xxx"; // 真实
    public static String APP_ID_XXX = "xxx"; // 测试/第二个账号

    /**
     * AppSecret
     */
    public static String SECRET = "xxx"; // 真实
    public static String SECRET_XXX = "xxx"; // 测试/第二个账号

    /**
     * 商户号
     */
    public static final String MCH_ID = "xxx"; // 真实
    public static final String MCH_ID_XXX = "xxx"; // 测试/第二个账号


    /**
     * API密钥，在商户平台设置
     */
    public static final String API_KEY = "b7da078e4a8aed1aa0f9bcba473ba0bc"; // 真实
    public static final String API_KEY_XXX = "xxx"; // 测试/第二个账号
    public static final String API_KEY_SANDBOX = "xxx"; // sandbox_signkey

    /**
     * 证书路径
     */
    public static String APICLIENT_CERT = "/data/ops/cert/apiclient_cert.p12"; // 真实
    public static String APICLIENT_CERT_XXX = "/data/ops/cert_xxx/apiclient_cert.p12"; // 真实

    /**
     * 交易类型
     * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
     * MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
     */
    public static final String TRADE_TYPE =  "JSAPI";
    public static final String TRADE_TYPE_APP =  "APP";
    public static final String TRADE_TYPE_NATIVE =  "NATIVE";

    /**
     * 作用：企业付款到零钱资金使用商户号余额资金<br>
     * 场景：用于企业向微信用户个人付款
     */
    public static final String TRANSFERS_URL_SUFFIX = "/mmpaymkttransfers/promotion/transfers";
    /**
     * 作用：商户平台-现金红包-发放普通红包<br>
     * 场景：现金红包发放后会以公众号消息的形式触达用户
     * 其他：需要证书
     */
    public static final String SENDREDPACK_URL_SUFFIX = "/mmpaymkttransfers/sendredpack";

}

