package com.geyl.vo;

import com.geyl.bean.PageRequest;
import lombok.Data;

/**
 * @author geyl
 * @Package com.geyl.vo
 * @date 2019-4-12 15:46
 */
@Data
public class StoreUserVO extends PageRequest {
    private String userId;
    private String goodsId;
    private String userName;
    //推广人数
    private String countNum;
    //微信昵称
    private String nickName;
    private String phone;
    private String avatar;
}
