package com.geyl.controller;

import com.geyl.bean.Result;
import com.geyl.exception.MyException;
import com.geyl.service.ActivityService;
import com.geyl.service.WxService;
import com.geyl.vo.OrderAdd;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
     * 微信授权
     */
    @GetMapping("getCode/{goodsId}/{pid}")
    public Result getCode(@PathVariable String goodsId,@PathVariable String pid){
        return Result.OK(wxService.getRequestCodeUrl(goodsId,pid));
    }
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
     * 查询活动后台数据
     *
     * @param goodsId
     * @return
     */
    @GetMapping("manage/{userId}/{goodsId}")
    public Result getActivityData(@PathVariable String userId, @PathVariable String goodsId) throws MyException {
        return Result.OK(activityService.getActivityData(userId, goodsId));
    }

    /**
     * 获取用户信息
     *
     * @param code
     * @return
     */
    @PostMapping("user/add")
    public Result addUser(@RequestParam("code") String code, @RequestParam("goodsId") Integer goodsId) {
        return activityService.addUser(code, goodsId);
    }

    /**
     * 下单
     */
    @PostMapping("order/add")
    public Result addOrder(@RequestBody OrderAdd orderAdd) {
        return activityService.addOrder(orderAdd);
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
     * 核销
     */
    @PostMapping("order/close")
    public Result closeOrder(@RequestParam("userId") String userId, @RequestParam("orderCode") String orderCode) throws MyException {
        return activityService.closeOrder(userId, orderCode);
    }


    /**
     * 异步回调
     */
    @PostMapping(value = "/notify")
    public void notify(@RequestBody String notifyData) {
        log.info("【异步回调】request={}", notifyData);
        PayResponse response = bestPayService.asyncNotify(notifyData);
        if (response != null) {
            //TODO 更新订单状态
            activityService.updateOrderStatus(response.getOrderId(), response.getOutTradeNo());
            //判断是否返现
            activityService.rewardRed(response.getOrderId());
        }
        log.info("【异步回调】response={}", JsonUtil.toJson(response));
    }

    /**
     * 商家合作
     *
     * @return
     */
    @PostMapping("store/add")
    public Result addStore(@RequestParam("userName") String userName, @RequestParam("phone") String phone) {
        return activityService.addStore(userName, phone);
    }

    @PostMapping("getSign")
    public Result getSign(@RequestParam("url") String url){
        return wxService.getSign(url);
    }

    @ResponseBody
    @RequestMapping(value = "/wxsign", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public String getWxUserInfo(HttpServletRequest request,
                                @RequestParam(required = true) String echostr,
                                @RequestParam(required = false) String signature,
                                @RequestParam(required = false) String timestamp,
                                @RequestParam(required = false) String nonce) {
        try {
            //只需要把微信请求的 echostr, 返回给微信就可以了
            log.info("测试来过===================" + echostr);
            log.info("测试来过===================" + signature);
            log.info("测试来过===================" + timestamp);
            log.info("测试来过===================" + nonce);
            return echostr;
        } catch (Exception e) {
            log.info("测试微信公众号的接口配置信息发生异常：", e);
            return "错误！！！";
        }

    }
}
