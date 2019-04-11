package com.geyl.util.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author geyl
 * @date 2019-4-10 17:31
 */
@Component
@Slf4j
public class QinNiuUtil {
    public String uploadFile(InputStream input) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.autoZone());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "oRBoLkFb30EBA3NVkklU2T0UuTlRGABuRZknmFDg";
        String secretKey = "NOJd5QLzOJQZ88X5vEx7wAIMawtX67Fvy5qljzN4";
        String bucket = "geyl_file";
        //密钥配置
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(input, null, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info(putRet.hash);
            return "http://ppqnam2qx.bkt.clouddn.com/" + putRet.hash;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }


    /*public static void main(String[] args) {
        File file = new File("D:\\流程图.jpg");
        try {
            FileInputStream stream = new FileInputStream(file);
            uploadFile(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

}
