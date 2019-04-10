package com.geyl.exception;

import com.geyl.bean.Result;
import com.geyl.bean.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "common/error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView businessExceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws IOException {
        String accept = req.getHeader("Accept");
        String requestType = req.getHeader("X-Requested-With");
        ModelAndView mav = new ModelAndView();
        boolean ajax = (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) ? true : false;
        if(ajax || accept.contains("json")) {     //ajax请求或者请求端接受json数据
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("code", -1);
            result.put("msg", e.getMessage());
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            view.setAttributesMap(result);
            mav.setView(view);
            return mav;
        } else {
            log.error(e.getMessage());
            mav.addObject("message", e);
            mav.addObject("url", req.getRequestURL());
            mav.setViewName(DEFAULT_ERROR_VIEW);
            return mav;
        }
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result<String> jsonErrorHandler(HttpServletRequest req, MyException e){
        Result<String> r = new Result<>();
        log.error(e.getMessage());
        e.printStackTrace();
        r.setMsg(e.getMessage());
        r.setCode(ResultCode.FAIL.code);
        return r;
    }

}

