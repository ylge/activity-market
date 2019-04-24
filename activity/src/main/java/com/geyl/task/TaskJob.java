package com.geyl.task;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author geyl
 * @Package com.geyl.task
 * @Description: 定时任务
 * @date 2019-4-23 17:37
 */
@Component
@Slf4j
public class TaskJob {
    @Value("${wx_appid}")
    private String wx_appid;
    @Value(value = "${wx_secret}")
    private String wx_secret;
    @Autowired
    private RestTemplate restTemplate;

    public static Map<String, String> wx_map = new HashMap<>();

    //刷新access_token 100分钟刷新一次,服务器启动的时候刷新一次（access_token有效期是120分钟，我设置的是每100分钟刷新一次）
    @Scheduled(initialDelay = 1000, fixedDelay = 100 * 60 * 1000)

    public void get_access_token() {
        try {
            String appid = wx_appid;
            String appsecret = wx_secret;
            String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
            requestUrl = requestUrl.replace("APPID", appid).replace("APPSECRET", appsecret);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String strbody = restTemplate.exchange(requestUrl.replace("\"", ""), HttpMethod.GET, entity, String.class)
                    .getBody();
            log.info(strbody);
            JSONObject jsonObject = JSONObject.parseObject(strbody);
            if (jsonObject.getString("access_token") != null) {
                try {
                    wx_map.put("access_token", jsonObject.getString("access_token"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            } else {
                log.info("定时刷新access_token失败，微信返回的信息是" + jsonObject.toJSONString());
            }
        } catch (Exception e) {
            log.info("更新access_token的过程当中发生了异常，异常的信息是" + e.getMessage());
        }
    }
}
