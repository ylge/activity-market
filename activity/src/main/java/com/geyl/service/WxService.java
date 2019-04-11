package com.geyl.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.geyl.bean.model.ClientUser;
import com.geyl.dao.ClientUserMapper;
import com.geyl.dao.OrderInfoMapper;
import com.geyl.exception.MyException;
import com.geyl.vo.OrderInfoVO;
import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private String wx_appid;
    @NacosValue(value = "${wx_secret:0c1a156ec46de1943d2bd6b3eb157e79}", autoRefreshed = true)
    private String wx_secret;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private ClientUserMapper clientUserMapper;

    public Object getSession(String code) throws MyException {
        Map<String, Object> response = new HashMap<>();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wx_appid +
                "&secret=" + wx_secret + "&js_code=" + code + "&grant_type=authorization_code";
        StringBuilder result = this.httpGetRequest(url);
        String responseEntity = result.toString();
        if (StringUtils.isEmpty(responseEntity)) {
            throw new MyException("连接微信失败");
        }
        JSONObject jsonObject = JSONObject.parseObject(responseEntity);
        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(sessionKey) || "undefined".equals(openid)) {
            log.error("jscode2session返回的结果;{}", responseEntity);
            throw new MyException("获取微信信息失败");
        }
        response.put("openid", openid);
        response.put("sessionKey", sessionKey);
        createUser(openid);
        return response;
    }

    public void createUser(String openid){
        ClientUser clientUser = new ClientUser();
        clientUser.setStatus(1);
        clientUser.setOpenid(openid);
        clientUserMapper.insertSelective(clientUser);
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

    /**
     * 进行HTTP里的get请求
     *
     * @param url
     * @return 请求返回的参数
     */
    public StringBuilder httpGetRequest(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            //创建地址对象
            URL u = new URL(url);
            //创建HttpURLConnection链接对象
            HttpURLConnection huconn = (HttpURLConnection) u.openConnection();
            //连接服务器
            huconn.connect();
            // 取得输入流，并使用Reader读取，设定字符编码
            in = new BufferedReader(new InputStreamReader(huconn.getInputStream(), "UTF-8"));
            String line;
            //读取返回值，直到为空
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
