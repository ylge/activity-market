package com.geyl.controller;

import com.geyl.bean.Result;
import com.geyl.exception.MyException;
import com.geyl.service.ActivityService;
import com.geyl.service.WxService;
import com.geyl.vo.OrderInfoVO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author geyl
 * @Package com.geyl.controller
 * @Description: 手机端相关接口
 * @date 2019-4-11 15:15
 */
@RestController
@RequestMapping(value = "/h5/activity/")
@Slf4j
public class ActivityFrontController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private WxService wxService;
    @Autowired
    private BestPayServiceImpl bestPayService;

    /**
     * 查询活动页面数据
     *
     * @param goodsId
     * @return
     */
    @GetMapping("goods/{goodsId}")
    public Result getActivityGoods(@PathVariable String goodsId) {
        return Result.OK(activityService.getGoodsDetail(goodsId));
    }

    /**
     * 获取授权code
     *
     * @param code
     * @return
     * @throws MyException
     */
    @GetMapping("getOpenid")
    public Result getOpenId(@RequestParam("code") String code) throws MyException {
        return Result.OK(wxService.getSession(code));
    }

    /**
     * 下单
     */
    @PostMapping("order/add")
    public Result addOrder(OrderInfoVO orderInfoVO) {
        return activityService.addOrder(orderInfoVO);
    }

    /**
     * 微信支付参数获取
     *
     * @param orderNo
     * @param openid
     * @return
     */
    @GetMapping(value = "payInfo/{orderNo}/{openid}")
    public Object getPayInfo(@PathVariable String orderNo, @PathVariable String openid) {
        return wxService.getPayInfo(orderNo, openid);
    }

    /**
     * 异步回调
     */
    @PostMapping(value = "/notify")
    public void notify(@RequestBody String notifyData) {
        log.info("【异步回调】request={}", notifyData);
        PayResponse response = bestPayService.asyncNotify(notifyData);
        log.info("【异步回调】response={}", JsonUtil.toJson(response));
    }
}
