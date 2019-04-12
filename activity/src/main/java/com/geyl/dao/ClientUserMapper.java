package com.geyl.dao;

import com.geyl.base.BaseMapper;
import com.geyl.bean.model.ClientUser;

public interface ClientUserMapper extends BaseMapper<ClientUser,String> {
    ClientUser getUserByOpenid(String openid);
}