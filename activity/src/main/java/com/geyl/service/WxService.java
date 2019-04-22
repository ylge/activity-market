package com.geyl.service;

import com.geyl.bean.wx.WxResponse;
import com.geyl.bean.wx.WxUserResponse;
import com.geyl.dao.OrderInfoMapper;
import com.geyl.vo.OrderInfoVO;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private OrderInfoMapper orderInfoMapper;
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

    WxResponse getSession(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        StringBuffer info = appendUrl(code);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String strbody = restTemplate.exchange(info.toString().replace("\"", ""), HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(strbody);
        return JsonUtil.toObject(strbody, WxResponse.class);
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
     * @param orderNo 订单id
     * @return o
     */
    public PayResponse getPayInfo(String orderNo, String openid) {
        OrderInfoVO orderInfo = orderInfoMapper.getOrderDetailByNo(orderNo);
        PayRequest request = new PayRequest();
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        request.setOrderId(orderNo);
        request.setOrderAmount(orderInfo.getOrderAmount().doubleValue());
        request.setOrderName(orderInfo.getGoodsName());
        request.setOpenid(openid);

        PayResponse response = bestPayService.pay(request);
        log.info(JsonUtil.toJson(response));
        return response;
    }

    public WxUserResponse getUserInfo(String openid, String access_token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String info = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String strbody = restTemplate.exchange(info, HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(strbody);
        return JsonUtil.toObject(strbody, WxUserResponse.class);
    }
}
