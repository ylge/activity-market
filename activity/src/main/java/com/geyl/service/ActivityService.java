package com.geyl.service;

import com.geyl.base.BaseMapper;
import com.geyl.base.impl.BaseServiceImpl;
import com.geyl.bean.PageResult;
import com.geyl.bean.Result;
import com.geyl.bean.model.*;
import com.geyl.bean.wx.WxResponse;
import com.geyl.bean.wx.WxUserResponse;
import com.geyl.dao.*;
import com.geyl.exception.MyException;
import com.geyl.util.CamelCaseUtil;
import com.geyl.util.FileUploadUtil;
import com.geyl.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
     *
     * @param activityGoods
     * @param goodsFile
     * @param goodsDetailFile
     * @param storeCodeFile
     * @param activityMusicFile
     * @param backgroundImageFile
     * @return
     * @throws IOException
     */
    public Result save(ActivityGoodsVO activityGoods, MultipartFile goodsFile, MultipartFile goodsDetailFile, MultipartFile storeCodeFile, MultipartFile activityMusicFile, MultipartFile backgroundImageFile) throws IOException {
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
        //音乐
        if (activityMusicFile.getSize() > 0) {
            String url = fileUploadUtil.upload(activityMusicFile.getInputStream());
            activityGoods.setActivityMusic(url);
        }
        //背景图
        if (backgroundImageFile.getSize() > 0) {
            String url = fileUploadUtil.upload(backgroundImageFile.getInputStream());
            activityGoods.setBackgroundImage(url);
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
        activityGoodsVO.setScan_user(scanRecordMapper.getActivityUser(goodsId));
        activityGoodsVO.setJoin_user(orderInfoMapper.getJoinUser(goodsId));
        List<RewardVO> rewardVOS = userAccountRecordMapper.getActivityUserRed(goodsId);
        rewardVOS.sort(Comparator.comparing(RewardVO::getRewardAmount).reversed());
        activityGoodsVO.setReward_list(rewardVOS);
        return activityGoodsVO;
    }

    /**
     * 关闭活动
     *
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
     *
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
        String orderNo = "YSG" + UUID.randomUUID().toString().substring(0, 8);
        orderInfo.setOrderNo(orderNo);
        orderInfo.setOrderCode(orderNo);
        orderInfoMapper.insertSelective(orderInfo);
        //去微信预下单
        PayResponse payResponse = wxService.getPayInfo(orderNo,clientUser.getOpenid());
        return Result.OK(payResponse);
    }

    /**
     * 获取用户微信信息
     * 创建系统用户
     * 创建浏览记录
     *
     * @param code
     * @param goodsId
     * @return
     */
    public Result addUser(String code, Integer goodsId) {
        boolean isManager = false;
        //获取ipenid
        WxResponse wxResponse = wxService.getSession(code);
        //判断用户是否新用户
        ClientUser clientUser = clientUserMapper.getUserByOpenid(wxResponse.getOpenid());
        if (clientUser == null) {
            //获取用户头像
            WxUserResponse userResponse = wxService.getUserInfo(wxResponse.getOpenid(),wxResponse.getAccess_token());
            clientUser = new ClientUser();
            clientUser.setStatus(1);
            clientUser.setOpenid(wxResponse.getOpenid());
            clientUser.setNickName(userResponse.getNickname());
            clientUser.setAvatar(userResponse.getHeadimgurl().replaceAll("\\\\",""));
//            clientUser.setGoodsId(goodsId);
            clientUserMapper.insertSelective(clientUser);
        }else{
            if(clientUser.getGoodsId().equals(goodsId)){
                isManager =true;
            }
        }
        ScanRecord scanRecord = scanRecordMapper.getRecordByUserId(clientUser.getUserId());
        if (scanRecord == null) {
            //浏览记录
            scanRecord = new ScanRecord();
            scanRecord.setGoodsId(goodsId);
            scanRecord.setUserId(clientUser.getUserId());
            scanRecord.setCreateTime(new Date());
            scanRecordMapper.insertSelective(scanRecord);
        } else {
            scanRecord.setUpdateTime(new Date());
            scanRecordMapper.updateByPrimaryKeySelective(scanRecord);
        }
        ClientUserVO clientUserVO = new ClientUserVO();
        clientUserVO.setOpenid(wxResponse.getOpenid());
        clientUserVO.setUserId(clientUser.getUserId());
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setUserId(clientUser.getUserId().toString());
        orderInfoVO.setGoodsId(goodsId.toString());
        int i = orderInfoMapper.checkIsOrder(orderInfoVO);
        clientUserVO.setOrder(i>=0);
        clientUserVO.setManager(isManager);
        return Result.OK(clientUserVO);
    }

    /**
     * 更新订单状态
     *
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
        int i = userAccountRecordMapper.getGetRewardInfoByOrderNo(orderInfoVO.getOrderNo());
        if (i > 0) {//已经返现
            return;
        }
        if (orderInfoVO.getPUserId() != null) {
            //TODO 发现金红包
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

    public ActivityManageVO getActivityData(String userId, String goodsId) throws MyException {
        ClientUser clientUser = clientUserMapper.selectByPrimaryKey(userId);
        if (clientUser == null || clientUser.getGoodsId()==null|| clientUser.getGoodsId() != Integer.parseInt(goodsId)) {
            throw new MyException("用户信息有误");
        }
        ActivityManageVO manageVO = orderInfoMapper.getActivityData(goodsId);
        manageVO.setOrder_list(orderInfoMapper.getOrderList(goodsId));
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("goodsId", goodsId);
        manageVO.setStore_user(orderInfoMapper.getStoreUser(param));
        return manageVO;
    }

    public Result closeOrder(String userId, String orderCode) throws MyException {
        ClientUser clientUser = clientUserMapper.selectByPrimaryKey(userId);
        if (clientUser == null || clientUser.getGoodsId() == 0) {
            throw new MyException("用户信息有误");
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(orderCode);
        orderInfo.setStatus(3);
        orderInfo.setGoodsId(clientUser.getGoodsId().toString());
        orderInfo.setUpdateTime(new Date());
        int i = orderInfoMapper.updateOrderStatusByOrderNo(orderInfo);
        if (i == 0) {
            throw new MyException("该核销码不存在");
        }
        return Result.OK();
    }

    public PageResult<StoreUserVO> getStoreUser(StoreUserVO storeUserVO) {
        Integer offset = storeUserVO.getOffset();
        Integer limit = storeUserVO.getLimit();
        String order = storeUserVO.getOrder();
        String sort = storeUserVO.getSort();
        PageHelper.startPage(offset / limit + 1, limit, CamelCaseUtil.toUnderlineName(sort + " " + order));
        List<StoreUserVO> tList = scanRecordMapper.getStoreUserList(storeUserVO);
        return new PageResult<>(new PageInfo<>(tList));
    }

    public void updateStoreUser(Integer userId, Integer goodsId, Integer status) {
        ClientUser clientUser = new ClientUser();
        clientUser.setUserId(userId);
        if (status == 1) {
            clientUser.setGoodsId(goodsId);
        } else {
            clientUser.setGoodsId(0);
        }
        clientUserMapper.updateByPrimaryKeySelective(clientUser);
    }

    public List<IncomeVO> getIncomeListByGoodsId(String goodsId) {
        return orderInfoMapper.getIncomeListByGoodsId(goodsId);
    }

    public List<RewardVO> getWithdrawListByGoodsId(String goodsId) {
        return orderInfoMapper.getWithdrawListByGoodsId(goodsId);
    }

    public Result addStore(String userName, String phone) {
        StoreCooperate storeCooperate = new StoreCooperate();
        storeCooperate.setPhone(phone);
        storeCooperate.setUserName(userName);
        storeCooperate.setCreateTime(new Date());
        storeService.addStore(storeCooperate);
        return Result.OK();
    }
}
