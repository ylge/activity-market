package com.geyl.vo;

import com.geyl.bean.model.OrderInfo;
import lombok.Data;

/**
 * @author geyl
 * @date 2019-4-11 16:54
 */
@Data
public class OrderInfoVO extends OrderInfo {
    private String goodsName;
    private String phone;
    private String userName;
}
