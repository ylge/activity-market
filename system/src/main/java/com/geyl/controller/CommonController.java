package com.geyl.controller;

import com.geyl.bean.Result;
import com.geyl.exception.MyException;
import com.geyl.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author geyl
 * @Package com.geyl.controller
 * @date 2018-11-12 14:54
 */
@Controller
public class CommonController {
    @Autowired
    private FileUploadUtil fileUploadUtil;

    @PostMapping(value = "/file/upload")
    @ResponseBody
    public Object uploadHeadPic(HttpServletRequest request) throws MyException {
        String url = null;
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()){
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if(file!=null){
//                        url = fileUploadUtil.upload(file.getInputStream(), file.getOriginalFilename());
                        url = fileUploadUtil.upload(file.getInputStream());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException("上传文件失败");
        }
        return Result.OK(url);
    }
}
