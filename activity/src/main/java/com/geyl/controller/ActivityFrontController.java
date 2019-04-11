package com.geyl.controller;

import com.geyl.bean.Result;
import com.geyl.exception.MyException;
import com.geyl.service.ActivityService;
import com.geyl.service.WxService;
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
public class ActivityFrontController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private WxService wxService;

    /**
     * 查询活动页面数据
     * @param goodsId
     * @return
     */
    @GetMapping("goods/{goodsId}")
    public Result getActivityGoods(@PathVariable String goodsId) {
        return Result.OK(activityService.getGoodsDetail(goodsId));
    }

    /**
     * 获取授权code
     * @param code
     * @return
     * @throws MyException
     */
    @GetMapping("getOpenid")
    public Result getOpenId(@RequestParam("code") String code) throws MyException {
        return Result.OK(wxService.getSession(code));
    }

    @GetMapping(value = "payInfo/{orderNo}/{openid}")
    public Object getPayInfo(@PathVariable String orderNo,@PathVariable String openid){
        return wxService.getPayInfo(orderNo,openid);
    }
}
