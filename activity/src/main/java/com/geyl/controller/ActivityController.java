package com.geyl.controller;

import com.geyl.annotation.Log;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.service.ActivityService;
import com.geyl.vo.ActivityGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author geyl
 * @Description: 活动管理
 * @date 2019-4-11 9:46
 */
@Controller
@RequestMapping("/activity/goods")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 　* @description:活动列表
     * 　* @author geyl
     * 　* @date 2018-5-22 13:34
     *
     */
    @GetMapping(value = "list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("/activity/goods/list");
        return modelAndView;
    }

    @GetMapping(value = "page")
    public @ResponseBody
    PageResult<ActivityGoodsVO> page(ActivityGoodsVO activityGoodsVO) {
        return activityService.getPageList(activityGoodsVO);
    }

    /**
     * 　* @description:活动新增
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @GetMapping(value = "add")
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.setViewName("/activity/goods/add");
        return modelAndView;
    }

    /**
     * 　* @description:活动编辑
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @GetMapping(value = "edit/{goodsId}")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable String goodsId) {
        modelAndView.setViewName("/activity/goods/edit");
        modelAndView.addObject("goods", activityService.getGoodsDetail(goodsId));
        return modelAndView;
    }

    /**
     * 　* @description:新增活动
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @PostMapping(value = "add")
    public @ResponseBody
    Result addgoods(ActivityGoodsVO activityGoods,
                    @RequestParam("goodsFile") MultipartFile goodsFile,
                    @RequestParam("goodsDetailFile") MultipartFile goodsDetailFile,
                    @RequestParam("backgroundImageFile") MultipartFile backgroundImageFile,
                    @RequestParam("activityMusicFile") MultipartFile activityMusicFile,
                    @RequestParam("storeCodeFile") MultipartFile storeCodeFile) throws IOException {
        return activityService.save(activityGoods,goodsFile,goodsDetailFile,storeCodeFile,activityMusicFile,backgroundImageFile);
    }

    /**
     * 　* @description:编辑活动
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     *
     */
    @PostMapping(value = "update")
    public @ResponseBody
    Result updategoods(ActivityGoodsVO activityGoods,
                       @RequestParam("goodsFile") MultipartFile goodsFile,
                       @RequestParam("goodsDetailFile") MultipartFile goodsDetailFile,
                       @RequestParam("backgroundImageFile") MultipartFile backgroundImageFile,
                       @RequestParam("activityMusicFile") MultipartFile activityMusicFile,
                       @RequestParam("storeCodeFile") MultipartFile storeCodeFile) throws IOException {
        return activityService.save(activityGoods,goodsFile,goodsDetailFile,storeCodeFile, activityMusicFile,backgroundImageFile);
    }

    /**
     * 禁用
     * @return
     */
    @Log(desc = "关闭活动")
    @GetMapping(value = "delete/{goodsId}/{status}")
    public @ResponseBody
    Result delete(@PathVariable String goodsId,@PathVariable Integer status) {
        return activityService.closeActivityById(goodsId,status);
    }

}
