package com.geyl.service;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.geyl.bean.wx.WxResponse;
import com.geyl.dao.OrderInfoMapper;
import com.geyl.vo.OrderInfoVO;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geyl
 * @Package com.geyl.service
 * @date 2019-4-11 15:31
 */
@Service
@Slf4j
public class WxService {
    @NacosValue(value = "${wx_appid:wx120e0e2cd0abc422}", autoRefreshed = true)
    private static String wx_appid;
    @NacosValue(value = "${wx_secret:0c1a156ec46de1943d2bd6b3eb157e79}", autoRefreshed = true)
    private static String wx_secret;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private RestTemplate restTemplate;

    WxResponse getSession(String code) {
        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        StringBuffer info = appendUrl(code);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String strbody = restTemplate.exchange(info.toString().replace("\"", ""), HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(strbody);
        WxResponse wxResponse = JsonUtil.toObject(strbody,WxResponse.class);
        return wxResponse;
    }

    private static StringBuffer appendUrl(String code) {
        StringBuffer info = new StringBuffer("https://api.weixin.qq.com/sns/jscode2session?");
        info.append("appid=").append(wx_appid).append("&");
        info.append("secret=").append(wx_secret).append("&");
        info.append("js_code=").append(code).append("&");
        info.append("grant_type=").append("authorization_code");
        return info;
    }

    /**
     * 获取支付信息
     *
     * @param orderNo 订单id
     * @return o
     */
    public Object getPayInfo(String orderNo, String openid) {
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

}
