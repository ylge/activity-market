package com.geyl.util.alioss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.geyl.config.OssConfig;
import com.geyl.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Date;

/**
 * @author geyl
 * @Package com.ehcenter.kcygl.web.util
 * @Description: 阿里云oss文件存储工具
 * @date 2018-6-29 11:04
 */
@Component
@Slf4j
public class AliOssUtil {
    @Autowired
    private OssConfig ossConfig;

   /* public void createFilePath(String filePath, OSSClient client) {
        client.putObject(ossConfig.getBucketName(), ossConfig.getFilePath() + filePath, new ByteArrayInputStream(new byte[0]));
    }*/

    /**
     * 上传文件
     *
     * @param fileName
     * @param input
     * @return
     */
    public OssResult uploadFile(String fileName, InputStream input) {
        OssResult ossResult = new OssResult();
        OSSClient client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        String filepath = ossConfig.getFilePath() + DateUtil.dateFormat(new Date(),DateUtil.DATE_PATTERN1) + "/";
        try {
            /*boolean found = client.doesObjectExist(ossConfig.getBucketName(), filepath);
            if (!found) {
                createFilePath(fileName, client);
            }*/
            PutObjectResult result = client.putObject(ossConfig.getBucketName(), filepath + fileName, input);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            ossResult.setStatus(false);
            return ossResult;
        } finally {
            client.shutdown();
        }
        ossResult.setStatus(true);
        ossResult.setUrl(ossConfig.getUrl()+filepath+fileName);
        return ossResult;
    }

    /**
     * 删除文件
     * @param fileName
     */
    public void deleteFile(String fileName){
        OSSClient client = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        String deleteFilePath = fileName.substring(ossConfig.getUrl().length(), fileName.length());
        client.deleteObject(ossConfig.getBucketName(), deleteFilePath);
    }

}
