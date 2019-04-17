package com.geyl.vo;

import com.geyl.bean.model.ActivityGoods;
import lombok.Data;

import java.util.List;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @date 2019-4-11 10:20
 */
@Data
public class ActivityGoodsVO extends ActivityGoods {
    private String StoreName;
    private String storeImage;
    private String storeAddress;
    private String storePhone;
    private String linkName;
    private String storeCode;
    private String storeIncome;
    private String storeWithdraw;
    private List<ScanUserVO> scan_user;
    private List<JoinUserVO> join_user;
    private List<RewardVO> reward_list;

}
