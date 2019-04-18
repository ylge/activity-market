package com.geyl.vo;

import lombok.Data;

import java.util.List;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @date 2019-4-11 10:20
 */
@Data
public class ActivityManageVO {
    private String scanNumber;
    private String joinNumber;
    private String storeIncome;
    private String storeWithdraw;
    private Integer status;
    private List<StoreUserVO> store_user;
    private List<OrderInfoVO> order_list;

}
