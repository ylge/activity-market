package com.geyl.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author geyl
 * @Title: ${CLASS_NAME}
 * @Package com.ehcenter.kcygl.common.utils
 * @date 2018-7-17 15:40
 */
public class MapBaiduUtil {
    private static final String url = "http://api.map.baidu.com/geocoder/v2/?";
    private static final String getReverseLocationUrl = "http://api.map.baidu.com/geocoder?output=json";
    private static final String BAIDU_AK = "6WIDsiR60GjWD0u9qmz9OeGWxkBQjXdf";

    /**
     * 返回输入地址的经纬度坐标 key lng(经度),lat(纬度)
     */
    public static Map<String, Object> getGeocoderLatitude(String address) {
        BufferedReader in = null;
        try {
            Map paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("address", address);
            paramsMap.put("output", "json");
            paramsMap.put("ak", BAIDU_AK);
            String paramStr = toQueryString(paramsMap);
            URL tirc = new URL(url + paramStr + "&sn=" + encrypt(paramStr));

            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            Map map = (Map) JSON.parse(str);
            if (0!=(int)map.get("status")){
                return null;
            }
            Map result = (Map) map.get("result");
            /*if((int)result.get("comprehension")<50){
                System.out.println("有误差");
                return null;
            }*/
            return (Map) result.get("location");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    /**
     * 计算sn的值
     * @param paramsStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String paramsStr) throws UnsupportedEncodingException {
        /**
         * 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存
         * <key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存
         * <key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，
         * 但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.
         * com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，
         * paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
         */
        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + "FkytCBIZMi5kVhkfTBICZp02KGa1U5tk");
        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        // 调用下面的MD5方法得到最后的sn签名
        return MD5(tempStr);
    }

    /**
     * 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
     * @param md5
     * @return
     */
    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


    /**
     * 地图坐标反向解析
     * {
     *   "status": "OK",
     *   "result": {
     *     "location": {
     *       "lng": 121.550544,
     *       "lat": 31.22739
     *     },
     *     "formatted_address": "上海市浦东新区世纪大道2001号-1号楼-15楼",
     *     "business": "世纪公园,洋泾,源深体育中心",
     *     "addressComponent": {
     *       "city": "上海市",
     *       "direction": "附近",
     *       "distance": "8",
     *       "district": "浦东新区",
     *       "province": "上海市",
     *       "street": "世纪大道",
     *       "street_number": "2001号-1号楼-15楼"
     *     },
     *     "cityCode": 289
     *   }
     * }
     * @param lng
     * @param lat
     * @throws Exception
     */
    public static Map<String,String> getReverseLocation(String lat, String lng){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("ak",BAIDU_AK);
        hashMap.put("location",lat+","+lng);
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(getReverseLocationUrl + generateQueryParam(hashMap));
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            StringBuffer address = new StringBuffer();
            String read;
            while (!StringUtils.isBlank(read=bufferedReader.readLine())){
                address.append(read.trim());
            }

            hashMap.clear();
            System.out.print("百度地图反向解析结果:  ");
            System.out.println(address.toString());
            org.json.JSONObject addressJson = new org.json.JSONObject(address.toString());
            if (null != addressJson){
                if ("ok".equalsIgnoreCase(addressJson.getString("status"))){
                    addressJson = addressJson.optJSONObject("result");
                    hashMap.put("address",addressJson.getString("formatted_address"));

                    addressJson = addressJson.optJSONObject("addressComponent");
                    hashMap.put("province",addressJson.getString("province"));
                    hashMap.put("city",addressJson.getString("city"));
                    hashMap.put("district",addressJson.getString("district"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null!=bufferedReader){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hashMap;
    }

    private static String generateQueryParam(Map<String,String> paramMap){
        StringBuffer params = new StringBuffer();
        paramMap.entrySet().forEach(entry -> params.append("&").append(entry.getKey()).append("=").append(entry.getValue()));
        return params.toString();
    }


    public static void main(String args[]) {
        try {
//            System.out.println(getReverseLocation("31.22738971078817","121.55054432317488"));
            System.out.println(getGeocoderLatitude("南充市金鱼安置小区"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
