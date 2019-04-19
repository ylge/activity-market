package com.geyl.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.geyl.annotation.Log;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.service.ActivityService;
import com.geyl.vo.ActivityGoodsVO;
import com.geyl.vo.IncomeVO;
import com.geyl.vo.RewardVO;
import com.geyl.vo.StoreUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author geyl
 * @Description: 活动管理
 * @date 2019-4-11 9:46
 */
@Controller
@RequestMapping("/activity")
@Slf4j
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 　* @description:活动列表
     * 　* @author geyl
     * 　* @date 2018-5-22 13:34
     */
    @GetMapping(value = "goods/list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("/activity/goods/list");
        return modelAndView;
    }

    @GetMapping(value = "goods/page")
    public @ResponseBody
    PageResult<ActivityGoodsVO> page(ActivityGoodsVO activityGoodsVO) {
        return activityService.getPageList(activityGoodsVO);
    }

    /**
     * 　* @description:活动新增
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @GetMapping(value = "goods/add")
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.setViewName("/activity/goods/add");
        return modelAndView;
    }

    /**
     * 　* @description:活动编辑
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @GetMapping(value = "goods/edit/{goodsId}")
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
     */
    @PostMapping(value = "goods/add")
    public @ResponseBody
    Result addgoods(ActivityGoodsVO activityGoods,
                    @RequestParam("goodsFile") MultipartFile goodsFile,
                    @RequestParam("goodsDetailFile") MultipartFile goodsDetailFile,
                    @RequestParam("backgroundImageFile") MultipartFile backgroundImageFile,
                    @RequestParam("activityMusicFile") MultipartFile activityMusicFile,
                    @RequestParam("storeCodeFile") MultipartFile storeCodeFile) throws IOException {
        return activityService.save(activityGoods, goodsFile, goodsDetailFile, storeCodeFile, activityMusicFile, backgroundImageFile);
    }

    /**
     * 　* @description:编辑活动
     * 　* @param
     * 　* @return
     * 　* @author geyl
     * 　* @date 2018-5-22 13:35
     */
    @PostMapping(value = "goods/update")
    public @ResponseBody
    Result updategoods(ActivityGoodsVO activityGoods,
                       @RequestParam("goodsFile") MultipartFile goodsFile,
                       @RequestParam("goodsDetailFile") MultipartFile goodsDetailFile,
                       @RequestParam("backgroundImageFile") MultipartFile backgroundImageFile,
                       @RequestParam("activityMusicFile") MultipartFile activityMusicFile,
                       @RequestParam("storeCodeFile") MultipartFile storeCodeFile) throws IOException {
        return activityService.save(activityGoods, goodsFile, goodsDetailFile, storeCodeFile, activityMusicFile, backgroundImageFile);
    }

    /**
     * 禁用
     *
     * @return
     */
    @Log(desc = "关闭活动")
    @GetMapping(value = "goods/delete/{goodsId}/{status}")
    public @ResponseBody
    Result delete(@PathVariable String goodsId, @PathVariable Integer status) {
        return activityService.closeActivityById(goodsId, status);
    }

    /**
     * 　* @description:活动用户列表
     * 　* @author geyl
     * 　* @date 2018-5-22 13:34
     */
    @GetMapping(value = "user/manage/{goodsId}")
    public ModelAndView list(ModelAndView modelAndView, @PathVariable String goodsId) {
        modelAndView.setViewName("/activity/store/user_list");
        modelAndView.addObject(goodsId);
        return modelAndView;
    }

    /**
     * 查询活动用户列表
     *
     * @param storeUserVO
     * @return
     */
    @GetMapping(value = "user/page")
    public @ResponseBody
    PageResult<StoreUserVO> page(StoreUserVO storeUserVO) {
        return activityService.getStoreUser(storeUserVO);
    }

    /**
     * 店员设置
     *
     * @param goodsId
     * @return
     */
    @PostMapping(value = "user/delete")
    public @ResponseBody
    Result delete(@RequestParam Integer userId, @RequestParam Integer goodsId, @RequestParam Integer status) {
        activityService.updateStoreUser(userId, goodsId, status);
        return Result.OK();
    }

    /**
     * 活动收入列表导出
     */

    @GetMapping(value = "/report/income/{goodsId}")
    public void activityIncome(ModelMap modelMap, HttpServletRequest request, @PathVariable String goodsId,
                                         HttpServletResponse response) {
        ActivityGoodsVO activityGoodsVO = activityService.getGoodsDetail(goodsId);
        if(activityGoodsVO!=null){
            ExportParams params = new ExportParams(activityGoodsVO.getGoodsName()+"-收入列表", null, ExcelType.XSSF);
            modelMap.put(NormalExcelConstants.DATA_LIST, activityService.getIncomeListByGoodsId(goodsId));
            modelMap.put(NormalExcelConstants.CLASS, IncomeVO.class);
            modelMap.put(NormalExcelConstants.PARAMS, params);
            modelMap.put(NormalExcelConstants.FILE_NAME, activityGoodsVO.getGoodsName()+"-收入列表");
            PoiBaseView.render(modelMap, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
        }
    }
    /**
     * 活动收入列表导出
     */

    @GetMapping(value = "/report/withdraw/{goodsId}")
    public void activityWithdraw(ModelMap modelMap, HttpServletRequest request, @PathVariable String goodsId,
                               HttpServletResponse response) {
        ActivityGoodsVO activityGoodsVO = activityService.getGoodsDetail(goodsId);
        if(activityGoodsVO!=null){
            ExportParams params = new ExportParams(activityGoodsVO.getGoodsName()+"-支出列表", null, ExcelType.XSSF);
            modelMap.put(NormalExcelConstants.DATA_LIST, activityService.getWithdrawListByGoodsId(goodsId));
            modelMap.put(NormalExcelConstants.CLASS, RewardVO.class);
            modelMap.put(NormalExcelConstants.PARAMS, params);
            modelMap.put(NormalExcelConstants.FILE_NAME, activityGoodsVO.getGoodsName()+"-支出列表");
            PoiBaseView.render(modelMap, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
        }
    }

}
