package com.geyl.util;

import com.geyl.exception.MyException;
import com.geyl.util.alioss.AliOssUtil;
import com.geyl.util.alioss.OssResult;
import com.geyl.util.qiniu.QinNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author geyl
 * @date 2018-11-7 13:57
 */
@Component
public class FileUploadUtil {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private QinNiuUtil qinNiuUtil;

    /**
     * 阿里云
     * @param input
     * @param fileName
     * @return
     * @throws MyException
     */
    public String upload(InputStream input, String fileName) throws MyException {
        String uuid = UUID.randomUUID().toString();
        fileName = uuid.replaceAll("-", "") + fileName.substring(fileName.indexOf("."),fileName.length());
        OssResult ossResult = aliOssUtil.uploadFile(fileName, input);
        if (ossResult.isStatus()) {
            return ossResult.getUrl();
        } else {
            throw new MyException("上传图片失败");
        }
    }

    /**
     * 七牛云
     * @param input
     * @return
     */
    public String upload(InputStream input){
        return qinNiuUtil.uploadFile(input);
    }

}
