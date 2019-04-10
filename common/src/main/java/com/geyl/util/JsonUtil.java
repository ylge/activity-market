package com.geyl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author AlanSun
 * @Date 2017年2月19日 下午5:18:47
 */
public class JsonUtil {
	/**
	 * 类转String
	 * @param o
	 * @return
	 */
	public static <T> String class2JsonString(T o){
		return String.valueOf(JSON.toJSONString(o));
	}
	/**
	 * jsonString转list
	 * @param str
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonString2Array(String str, Class<T> clazz){
		return JSONObject.parseArray(str, clazz);
	}
}
