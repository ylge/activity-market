package com.geyl.service;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.*;
import com.geyl.bean.wx.WxResponse;
import com.geyl.dao.*;
import com.geyl.util.CamelCaseUtil;
import com.geyl.util.FileUploadUtil;
import com.geyl.vo.ActivityGoodsVO;
import com.geyl.vo.OrderAdd;
import com.geyl.vo.OrderInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ClientUserMapper clientUserMapper;
    @Autowired
    private WxService wxService;
    @Autowired
    private ScanRecordMapper scanRecordMapper;
    @Autowired
    private UserAccountRecordMapper userAccountRecordMapper;

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

    /**
     * 新建或编辑活动
     * @param activityGoods
     * @param goodsFile
     * @param goodsDetailFile
     * @param storeCodeFile
     * @return
     * @throws IOException
     */
    public Result save(ActivityGoodsVO activityGoods, MultipartFile goodsFile, MultipartFile goodsDetailFile, MultipartFile storeCodeFile) throws IOException {
        //商品图片
        if (goodsFile.getSize() > 0) {
            String url = fileUploadUtil.upload(goodsFile.getInputStream());
            activityGoods.setGoodsImage(url);
        }
        //商品详情
        if (goodsDetailFile.getSize() > 0) {
            String url = fileUploadUtil.upload(goodsDetailFile.getInputStream());
            activityGoods.setGoodsDetail(url);
        }
        //商家二维码
        if (storeCodeFile.getSize() > 0) {
            String url = fileUploadUtil.upload(storeCodeFile.getInputStream());
            activityGoods.setStoreCode(url);
        }
        if (activityGoods.getGoodsId() == null) {
            //新增店铺信息
            StoreInfo storeInfo = new StoreInfo();
            BeanUtils.copyProperties(activityGoods, storeInfo);
            storeService.insertSelective(storeInfo);
            activityGoods.setStoreId(storeInfo.getStoreId());
            //新增活动
            this.insertSelective(activityGoods);
        } else {
            this.updateByPrimaryKeySelective(activityGoods);
            StoreInfo storeInfo = new StoreInfo();
            BeanUtils.copyProperties(activityGoods, storeInfo);
            storeService.updateByPrimaryKeySelective(storeInfo);
        }
        return Result.OK();
    }

    public ActivityGoodsVO getGoodsDetail(String goodsId) {
        ActivityGoodsVO activityGoodsVO = activityGoodsMapper.getGoodsDetail(goodsId);
        activityGoodsVO.setScanUserVOS(scanRecordMapper.getActivityUser(goodsId));
        activityGoodsVO.setUserRedVOS(userAccountRecordMapper.getActivityUserRed(goodsId));
        return activityGoodsVO;
    }

    /**
     * 关闭活动
     * @param goodsId
     * @param status
     * @return
     */
    public Result closeActivityById(String goodsId, Integer status) {
        ActivityGoods activityGoods = new ActivityGoods();
        activityGoods.setGoodsId(goodsId);
        activityGoods.setStatus(status);
        this.updateByPrimaryKeySelective(activityGoods);
        return Result.OK();
    }

    /**
     * 下单
     * @param orderAdd
     * @return
     */
    public Result addOrder(OrderAdd orderAdd) {
        ActivityGoods activityGoods = activityGoodsMapper.selectByPrimaryKey(orderAdd.getGoodsId());
        ClientUser clientUser = clientUserMapper.selectByPrimaryKey(orderAdd.getUserId());
        if (clientUser == null) {//新建用户
            clientUser = new ClientUser();
            clientUser.setUserName(orderAdd.getUserName());
            clientUser.setPhone(orderAdd.getPhone());
            clientUser.setStatus(1);
            clientUser.setOpenid(orderAdd.getOpenid());
            clientUserMapper.insertSelective(clientUser);
            orderAdd.setUserId(clientUser.getUserId().toString());
        } else {
            clientUser.setUserName(orderAdd.getUserName());
            clientUser.setPhone(orderAdd.getPhone());
            clientUserMapper.updateByPrimaryKeySelective(clientUser);
        }
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderAdd, orderInfo);
        orderInfo.setBuyCount(1);
        orderInfo.setOrderAmount(activityGoods.getGoodsPrice());
        orderInfo.setCreateTime(new Date());
        orderInfo.setStatus(1);
        orderInfo.setPaymentAmount(activityGoods.getGoodsPrice());
        orderInfo.setStoreId(activityGoods.getStoreId());
        String orderNo = UUID.randomUUID().toString();
        orderInfo.setOrderNo(orderNo);
        orderInfo.setOrderCode(orderNo);
        orderInfoMapper.insertSelective(orderInfo);
        return Result.OK(orderInfo.getOrderNo());
    }

    /**
     * 获取用户微信信息
     * 创建系统用户
     * 创建浏览记录
     * @param code
     * @param goodsId
     * @return
     */
    public Result getOpenId(String code, String goodsId) {
        WxResponse wxResponse = wxService.getSession(code);
        //判断用户是否新用户
        ClientUser clientUser = clientUserMapper.getUserByOpenid(wxResponse.getOpenid());
        if (clientUser == null) {
            clientUser = new ClientUser();
            clientUser.setStatus(1);
            clientUser.setOpenid(wxResponse.getOpenid());
            clientUserMapper.insertSelective(clientUser);
        }
        ScanRecord scanRecord = scanRecordMapper.getRecordByUserId(clientUser.getUserId());
        if (scanRecord == null) {
            //浏览记录
            scanRecord = new ScanRecord();
            scanRecord.setGoodsId(Integer.parseInt(goodsId));
            scanRecord.setUserId(clientUser.getUserId());
            scanRecord.setCreateTime(new Date());
            scanRecordMapper.insertSelective(scanRecord);
        } else {
            scanRecord.setUpdateTime(new Date());
            scanRecordMapper.updateByPrimaryKeySelective(scanRecord);
        }
        return Result.OK(wxResponse);
    }

    /**
     * 更新订单状态
     * @param orderId
     * @param outTradeNo
     */
    public void updateOrderStatus(String orderId, String outTradeNo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(orderId);
        orderInfo.setStatus(2);
        orderInfo.setRemark1(outTradeNo);
        orderInfo.setUpdateTime(new Date());
        orderInfoMapper.updateOrderStatusByOrderNo(orderInfo);
    }

    public void rewardRed(String orderId) {
        OrderInfoVO orderInfoVO = orderInfoMapper.getOrderDetailByNo(orderId);
        if(orderInfoVO.getPUserId()!=null){
            //发红包
            UserAccountRecord userAccountRecord = new UserAccountRecord();
            userAccountRecord.setAmount(BigDecimal.ONE);
            userAccountRecord.setCreateTime(new Date());
            userAccountRecord.setTradeNo(orderInfoVO.getOrderNo());
            userAccountRecord.setUserId(orderInfoVO.getPUserId());
            userAccountRecord.setRemark1(orderInfoVO.getGoodsId());
            userAccountRecord.setRemark2(orderInfoVO.getUserId());
            userAccountRecordMapper.insert(userAccountRecord);
        }
    }
}
