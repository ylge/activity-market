package com.geyl.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @date 2019-4-12 15:44
 */
@Data
public class RewardVO {
    @Excel(name = "购买用户")
    private String userName;
    @Excel(name = "手机号")
    private String phone;
    private String avatar;
    private String childNum;
    @Excel(name = "佣金")
    private String rewardAmount;
}
