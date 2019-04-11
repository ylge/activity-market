package com.geyl.vo;

import com.geyl.bean.model.ActivityGoods;
import lombok.Data;

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

}
