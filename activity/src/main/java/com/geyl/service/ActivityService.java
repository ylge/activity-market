package com.geyl.service;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.ActivityGoods;
import com.geyl.bean.model.StoreInfo;
import com.geyl.dao.ActivityGoodsMapper;
import com.geyl.util.CamelCaseUtil;
import com.geyl.util.FileUploadUtil;
import com.geyl.vo.ActivityGoodsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author geyl
 * @date 2019-4-11 10:07
 */
@Service
public class ActivityService extends BaseServiceImpl<ActivityGoods, String> {

    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;
    @Autowired
    private StoreService storeService;
    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Override
    public BaseMapper<ActivityGoods, String> getMappser() {
        return activityGoodsMapper;
    }

    public PageResult<ActivityGoodsVO> getPageList(ActivityGoodsVO activityGoodsVO) {
        Integer offset = activityGoodsVO.getOffset();
        Integer limit = activityGoodsVO.getLimit();
        String order = activityGoodsVO.getOrder();
        String sort = activityGoodsVO.getSort();
        PageHelper.startPage(offset / limit + 1, limit, CamelCaseUtil.toUnderlineName(sort + " " + order));
        List<ActivityGoodsVO> tList = activityGoodsMapper.getAcitvityGoodsList(activityGoodsVO);
        return new PageResult<>(new PageInfo<>(tList));
    }

    public Result save(ActivityGoodsVO activityGoods, MultipartFile goodsFile, MultipartFile goodsDetailFile, MultipartFile storeCodeFile) throws IOException {
        //商品图片
        if(goodsFile.getSize()>0){
            String url = fileUploadUtil.upload(goodsFile.getInputStream());
            activityGoods.setGoodsImage(url);
        }
        //商品详情
        if(goodsDetailFile.getSize()>0){
            String url = fileUploadUtil.upload(goodsDetailFile.getInputStream());
            activityGoods.setGoodsDetail(url);
        }
        //商家二维码
        if(storeCodeFile.getSize()>0){
            String url = fileUploadUtil.upload(storeCodeFile.getInputStream());
            activityGoods.setStoreCode(url);
        }
        if(activityGoods.getGoodsId() == null){
            //新增店铺信息
            StoreInfo storeInfo = new StoreInfo();
            BeanUtils.copyProperties(activityGoods,storeInfo);
            storeService.insertSelective(storeInfo);
            activityGoods.setStoreId(storeInfo.getStoreId());
            //新增活动
            this.insertSelective(activityGoods);
        }else{
            this.updateByPrimaryKeySelective(activityGoods);
            StoreInfo storeInfo = new StoreInfo();
            BeanUtils.copyProperties(activityGoods,storeInfo);
            storeService.updateByPrimaryKeySelective(storeInfo);
        }
        return Result.OK();
    }

    public ActivityGoodsVO getGoodsDetail(String goodsId) {
        return activityGoodsMapper.getGoodsDetail(goodsId);
    }

    public Result closeActivityById(String goodsId, Integer status) {
        ActivityGoods activityGoods = new ActivityGoods();
        activityGoods.setGoodsId(goodsId);
        activityGoods.setStatus(status);
        this.updateByPrimaryKeySelective(activityGoods);
        return Result.OK();
    }
}
