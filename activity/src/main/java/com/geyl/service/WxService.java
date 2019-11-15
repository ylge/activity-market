package com.geyl.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geyl.bean.Result;
import com.geyl.bean.model.ActivityGoods;
import com.geyl.bean.model.OrderInfo;
import com.geyl.bean.model.paywallet.TransPayWallet;
import com.geyl.bean.model.paywallet.TransPayWalletInfo;
import com.geyl.bean.model.paywallet.TransPayWalletResult;
import com.geyl.bean.model.redpack.SendRedPack;
import com.geyl.bean.model.redpack.SendRedPackResult;
import com.geyl.bean.model.redpack.SendRedpackInfo;
import com.geyl.bean.wx.WxResponse;
import com.geyl.bean.wx.WxUserResponse;
import com.geyl.dao.ActivityGoodsMapper;
import com.geyl.exception.MyException;
import com.geyl.task.TaskJob;
import com.geyl.util.SignUtil;
import com.geyl.util.http.HttpsClient;
import com.geyl.util.http.Response;
import com.geyl.vo.OrderInfoVO;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.wxpay.WXPay;
import com.wxpay.WXPayConfigImpl;
import com.wxpay.WXPayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author geyl
 * @Package com.geyl.service
 * @date 2019-4-11 15:31
 */
@Service
@Slf4j
public class WxService {
    @Value("${wx_appid}")
    private String wx_appid;
    @Value(value = "${wx_secret}")
    private String wx_secret;
    @Value(value = "${redirectUrl}")
    private String redirectUrl;
    @Value(value = "${wechat.mchId}")
    private String wx_mchId;
    @Value(value = "${wechat.mchKey}")
    private String wx_mchKey;
    @Value(value = "${wechat.keyPath}")
    private String wx_keyPath;
    @Autowired
    private ActivityGoodsMapper goodsMapper;
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 生成用于获取access_token的Code的Url
     *
     * @return
     */
    public String getRequestCodeUrl(String goodsId, String pid) {
        String redirect_url = redirectUrl + "?i=" + goodsId;
        if (pid != null) {
            redirect_url = redirect_url + "&pid=" + pid;
        }
        String return_url = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect",
                wx_appid, redirect_url, "snsapi_userinfo");
        log.info(return_url);
        return return_url;
    }

    /**
     * 获取授权
     *
     * @param code
     * @return
     */
    WxResponse getSession(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        StringBuffer info = appendUrl(code);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String strbody = restTemplate.exchange(info.toString().replace("\"", ""), HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(strbody);
        WxResponse wxResponse = JsonUtil.toObject(strbody, WxResponse.class);
        log.info("access_token:" + TaskJob.wx_map.get("access_token"));
        return wxResponse;
    }

    private StringBuffer appendUrl(String code) {
        StringBuffer info = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token?");
        info.append("appid=").append(wx_appid).append("&");
        info.append("secret=").append(wx_secret).append("&");
        info.append("code=").append(code).append("&");
        info.append("grant_type=").append("authorization_code");
        return info;
    }

    /**
     * 获取支付信息
     *
     * @param order 订单
     * @return o
     */
    public PayResponse getPayInfo(OrderInfo order, String openid) throws Exception {
        ActivityGoods activityGoods = goodsMapper.getGoodsDetail(order.getGoodsId());
        if (activityGoods.getStatus() != 1) {
            return null;
        }
        /*WXPay wxPay = new WXPay(WXPayConfigImpl.getInstance());
        Map<String, String> data = new HashMap<>();
        data.put("body", "亿时光");
        data.put("out_trade_no", order.getOrderNo());
        data.put("fee_type", "CNY");
        data.put("total_fee", order.getOrderAmount().toString());
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", WXPayConstants.TRADE_TYPE);  // 此处指定为扫码支付
        data.put("openid", openid);

        Map<String, String> resp = wxPay.unifiedOrder(data);
        System.out.println(resp);*/
        PayRequest request = new PayRequest();
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_MWEB);
        request.setOrderId(order.getOrderNo());
        request.setOrderAmount(order.getOrderAmount().doubleValue());
        request.setOrderName(activityGoods.getGoodsName());
        request.setOpenid(openid);

        PayResponse response = bestPayService.pay(request);
        response.setOrderId(order.getOrderNo());
        log.info(JsonUtil.toJson(response));
        return response;
    }

    /**
     * 获取用户信息
     *
     * @param openid
     * @param access_token
     * @return
     */
    public WxUserResponse getUserInfo(String openid, String access_token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String info = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String strbody = restTemplate.exchange(info, HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(strbody);
        return JsonUtil.toObject(strbody, WxUserResponse.class);
    }

    /**
     * token获取jsapi_ticket
     *
     * @return
     */
    public Object getJsapiTicket() {
        String access_token = TaskJob.wx_map.get("access_token");
        if (access_token == null) {
            access_token = getWxToken();
        }
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token
                + "&type=jsapi";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String strbody = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(strbody);
        // 将JSON转换为Map
        Map parseParam = (Map) JSON.parse(strbody);
        return parseParam.get("ticket");
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getWxToken() {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        requestUrl = requestUrl.replace("APPID", wx_appid).replace("APPSECRET", wx_secret);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String strbody = restTemplate.exchange(requestUrl.replace("\"", ""), HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(strbody);
        JSONObject jsonObject = JSONObject.parseObject(strbody);
        if (jsonObject.getString("access_token") != null) {
            TaskJob.wx_map.put("access_token", jsonObject.getString("access_token"));
        }
        return jsonObject.getString("access_token");
    }

    /**
     * 网页端当前的url
     */
    public Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", wx_appid);
        return ret;
    }

    public Result getSign(String url) {
        String ticket = (String) getJsapiTicket();
        Map map = sign(ticket, url);
        return Result.OK(map);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 企业付款到零钱
     */
    /**
     * 企业付款到零钱
     *
     * @param transPayWallet 企业付款到零钱信息
     * @return 企业付款到零钱结果
     */
    public TransPayWalletResult transPayWallet(TransPayWallet transPayWallet) throws MyException {
        //获取微信支付配置
        //创建RSA公钥请求对象
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //数据签名
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_appid", wx_appid);
        map.put("mchid", wx_mchId);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", transPayWallet.getPartner_trade_no());
        map.put("openid", transPayWallet.getOpenid());
        //不校验真实姓名
        map.put("check_name", "NO_CHECK");
        map.put("amount", transPayWallet.getAmount() + "");
        map.put("desc", transPayWallet.getDesc());
        map.put("spbill_create_ip", transPayWallet.getSpbill_create_ip());
        //数据签名
        String sign = SignUtil.getSign(map, wx_mchKey);
        //封装提交数据
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<mch_appid>").append(transPayWallet.getMch_appid()).append("</mch_appid>");
        sb.append("<mchid>").append(wx_mchId).append("</mchid>");
        sb.append("<nonce_str>").append(nonceStr).append("</nonce_str>");
        sb.append("<partner_trade_no>").append(transPayWallet.getPartner_trade_no()).append("</partner_trade_no>");
        sb.append("<openid>").append(transPayWallet.getOpenid()).append("</openid>");
        sb.append("<check_name>NO_CHECK</check_name>");
        sb.append("<amount>").append(transPayWallet.getAmount()).append("</amount>");
        sb.append("<desc>").append(transPayWallet.getDesc()).append("</desc>");
        sb.append("<spbill_create_ip>").append(transPayWallet.getSpbill_create_ip()).append("</spbill_create_ip>");
        sb.append("<sign>").append(sign).append("</sign>");
        sb.append("</xml>");
        String xml = sb.toString();
        System.out.println("调试模式_企业付款到零钱接口 提交XML数据：" + xml);
        HttpsClient httpsClient = new HttpsClient();
        //发起请求，企业付款到零钱API
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", xml, wx_mchId, wx_keyPath, wx_secret);
        //获取返回内容
        String xmlResult = response.asString();
        System.out.println("调试模式_企业付款到零钱接口 返回XML数据：" + xmlResult);
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(TransPayWalletResult.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            TransPayWalletResult transPayWalletResult = (TransPayWalletResult) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(transPayWalletResult.getReturn_code())) {
                log.info("微信返回：" + transPayWalletResult.getReturn_msg());
                throw new MyException(transPayWalletResult.getReturn_msg());
            }
            return transPayWalletResult;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }

    /**
     * 查询企业付款到零钱信息
     *
     * @param partnerTradeNo 商户企业付款单号
     * @param appid          微信公众帐号ID
     * @return 企业付款到零钱信息
     */
    public TransPayWalletInfo getTransferInfo(String partnerTradeNo, String appid) throws MyException {
        //获取微信支付配置
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //数据签名
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_id", wx_mchId);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", partnerTradeNo);
        map.put("appid", appid);
        //数据签名
        String sign = SignUtil.getSign(map, wx_mchKey);
        //封装提交数据
        String xml = "<xml>"
                + "<mch_id>" + wx_mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<partner_trade_no>" + partnerTradeNo + "</partner_trade_no>"
                + "<appid>" + appid + "</appid>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";
        //发起请求，查询企业付款到零钱结果
        HttpsClient httpsClient = new HttpsClient();
        System.out.println("调试模式_查询企业付款到零钱接口 提交XML数据：" + xml);
        //发起请求，查询企业付款到零钱API
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo", xml, wx_mchId, wx_keyPath, wx_secret);
        //获取返回内容
        String xmlResult = response.asString();
        System.out.println("调试模式_查询企业付款到零钱接口 返回XML数据：" + xmlResult);
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(TransPayWalletInfo.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            TransPayWalletInfo transPayWalletInfo = (TransPayWalletInfo) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(transPayWalletInfo.getReturn_code())) {
                log.info("微信返回：" + transPayWalletInfo.getReturn_msg());
                throw new MyException(transPayWalletInfo.getReturn_msg());
            }
            return transPayWalletInfo;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }

    /**
     * 发放普通红包
     *
     * @param sendRedPack 现金红包对象
     * @return 发放普通红包返回结果对象
     */
    public SendRedPackResult sendRedPack(SendRedPack sendRedPack) throws MyException {
        //获取微信支付配置
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //签名数据
        sendRedPack.setWxappid(wx_appid);
        Map<String, String> map = new HashMap<String, String>();
        map.put("nonce_str", nonceStr);
        map.put("mch_billno", sendRedPack.getMch_billno());
        map.put("mch_id", wx_mchId);
        map.put("wxappid", sendRedPack.getWxappid());
        map.put("send_name", sendRedPack.getSend_name());
        map.put("re_openid", sendRedPack.getRe_openid());
        map.put("total_amount", sendRedPack.getTotal_amount() + "");
        map.put("total_num", "1");
        map.put("wishing", sendRedPack.getWishing());
        map.put("client_ip", sendRedPack.getClient_ip());
        map.put("act_name", sendRedPack.getAct_name());
        map.put("remark", sendRedPack.getRemark());
        //进行数据签名
        String sign = SignUtil.getSign(map, wx_mchKey);
        //将统一下单对象转成XML
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<sign><![CDATA[").append(sign).append("]]></sign>");
        sb.append("<mch_billno><![CDATA[").append(sendRedPack.getMch_billno()).append("]]></mch_billno>");
        sb.append("<mch_id><![CDATA[").append(wx_mchId).append("]]></mch_id>");
        sb.append("<wxappid><![CDATA[").append(sendRedPack.getWxappid()).append("]]></wxappid>");
        sb.append("<send_name><![CDATA[").append(sendRedPack.getSend_name()).append("]]></send_name>");
        sb.append("<re_openid><![CDATA[").append(sendRedPack.getRe_openid()).append("]]></re_openid>");
        sb.append("<total_amount><![CDATA[").append(sendRedPack.getTotal_amount()).append("]]></total_amount>");
        sb.append("<total_num><![CDATA[").append(1).append("]]></total_num>");
        sb.append("<wishing><![CDATA[").append(sendRedPack.getWishing()).append("]]></wishing>");
        sb.append("<client_ip><![CDATA[").append(sendRedPack.getClient_ip()).append("]]></client_ip>");
        sb.append("<act_name><![CDATA[").append(sendRedPack.getAct_name()).append("]]></act_name>");
        sb.append("<remark><![CDATA[").append(sendRedPack.getRemark()).append("]]></remark>");
        sb.append("<nonce_str><![CDATA[").append(nonceStr).append("]]></nonce_str>");
        sb.append("</xml>");
        String xml = sb.toString();
        System.out.println("调试模式_发送普通红包接口 提交XML数据：" + xml);
        //创建请求对象
        HttpsClient httpsClient = new HttpsClient();
        //发起请求，发送普通红包
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", xml, wx_mchId, wx_keyPath, wx_secret);
        //获取微信平台下单接口返回数据
        String xmlResult = response.asString();
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(SendRedPackResult.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            SendRedPackResult sendRedPackResult = (SendRedPackResult) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(sendRedPackResult.getReturn_code())) {
                log.error("微信返回" + sendRedPackResult.getReturn_msg());
                throw new MyException(sendRedPackResult.getReturn_msg());
            }
            return sendRedPackResult;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }

    /**
     * 查询红包记录
     *
     * @param mch_billno 商户订单号
     * @param appid      微信公众帐号ID
     * @return 红包发送记录
     * @throws MyException 微信服务异常
     */
    public SendRedpackInfo gethbinfo(String mch_billno, String appid) throws MyException {
        //获取微信支付配置
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //签名数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("nonce_str", nonceStr);
        map.put("mch_billno", mch_billno);
        map.put("mch_id", wx_mchId);
        map.put("appid", appid);
        map.put("bill_type", "MCHT");
        //进行数据签名
        String sign = SignUtil.getSign(map, wx_mchKey);
        //将统一下单对象转成XML
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<sign><![CDATA[").append(sign).append("]]></sign>");
        sb.append("<mch_billno><![CDATA[").append(mch_billno).append("]]></mch_billno>");
        sb.append("<mch_id><![CDATA[").append(wx_appid).append("]]></mch_id>");
        sb.append("<appid><![CDATA[").append(appid).append("]]></appid>");
        sb.append("<bill_type><![CDATA[").append("MCHT").append("]]></bill_type>");
        sb.append("<nonce_str><![CDATA[").append(nonceStr).append("]]></nonce_str>");
        sb.append("</xml>");
        String xml = sb.toString();
        System.out.println("调试模式_查询红包记录接口 提交XML数据：" + xml);
        //创建请求对象
        HttpsClient httpsClient = new HttpsClient();
        //发起请求，查询红包记录
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo", xml, wx_mchId, wx_keyPath, wx_secret);
        //获取微信平台下单接口返回数据
        String xmlResult = response.asString();
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(SendRedpackInfo.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            SendRedpackInfo sendRedpackInfo = (SendRedpackInfo) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(sendRedpackInfo.getReturn_code())) {
                log.error("微信返回" + sendRedpackInfo.getReturn_msg());
                throw new MyException(sendRedpackInfo.getReturn_msg());
            }
            return sendRedpackInfo;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }
}
