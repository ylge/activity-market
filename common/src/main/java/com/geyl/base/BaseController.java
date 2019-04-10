package com.geyl.base;


import com.geyl.bean.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * Created by Mr.Yangxiufeng on 2017/6/20.
 * Time:16:14
 * ProjectName:Common-admin
 */
public class BaseController {
    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected ShiroUser getUser(){
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }
}
