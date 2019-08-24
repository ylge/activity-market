package com.geyl.task;

import com.geyl.dao.ActivityGoodsMapper;
import com.geyl.service.WxService;
import com.geyl.vo.ActivityGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geyl
 * @Package com.geyl.task
 * @Description: 定时任务
 * @date 2019-4-23 17:37
 */
@Component
@Slf4j
public class TaskJob {
    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;
    @Autowired
    private WxService wxService;

    public static Map<String, String> wx_map = new HashMap<>();

    //刷新access_token 100分钟刷新一次,服务器启动的时候刷新一次（access_token有效期是120分钟，我设置的是每100分钟刷新一次）
    @Scheduled(initialDelay = 1000, fixedDelay = 100 * 60 * 1000)
    public void get_access_token() {
        log.info("更新微信access_token");
        String token = wxService.getWxToken();
        wx_map.put("access_token", token);
        log.info("更新微信access_token完成");
    }

    @Scheduled(cron = "0 30 0 ? * *")
    public void updateActivityStatus() {
        log.info(" >>开始执行活动状态处理");
        LocalDate local = LocalDate.now();//获取当前时间
        ActivityGoodsVO activityGoodsVO = new ActivityGoodsVO();
        activityGoodsVO.setStatus(1);
        activityGoodsVO.setEndTime(local.toString());
        List<ActivityGoodsVO> activityGoodsVOList = activityGoodsMapper.getAcitvityGoodsList(activityGoodsVO);
        activityGoodsVOList.forEach(activityGoods -> {
            activityGoods.setStatus(0);
            activityGoods.setUpdateTime(new Date());
            activityGoods.setUpdateBy("系统定时");
            activityGoodsMapper.updateByPrimaryKeySelective(activityGoods);
        });
        log.info(" >>结束执行活动状态处理");
    }
}
