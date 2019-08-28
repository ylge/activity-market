package com.geyl.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geyl.bean.Result;
import com.geyl.bean.model.ActivityGoods;
import com.geyl.bean.model.OrderInfo;
import com.geyl.bean.wx.WxResponse;
import com.geyl.bean.wx.WxUserResponse;
import com.geyl.dao.ActivityGoodsMapper;
import com.geyl.task.TaskJob;
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
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect",
                wx_appid, redirect_url, "snsapi_userinfo");
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
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        request.setOrderId(order.getOrderNo());
        request.setOrderAmount(order.getOrderAmount().doubleValue());
        request.setOrderName(activityGoods.getGoodsName());
        request.setOpenid(openid);

        PayResponse response = bestPayService.pay(request);
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
        log.info("请求的url:" + url);
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

}
