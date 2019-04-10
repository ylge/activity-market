package com.geyl.aop;

import com.alibaba.fastjson.JSON;
import com.geyl.annotation.Log;
import com.geyl.bean.ShiroUser;
import com.geyl.bean.model.system.SysOperationLog;
import com.geyl.service.SysOperationLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 　* @description:记录操作数据库的日志
 * 　* @author geyl
 * 　* @date 2018-5-23 14:27
 *
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private SysOperationLogService operationLogService;

    @Pointcut("@annotation(com.geyl.annotation.Log)")
    private void pointcut() {

    }

    @After("pointcut()")
    public void insertLogSuccess(JoinPoint jp) {
        addLog(jp, getDesc(jp));
    }

    private void addLog(JoinPoint jp, String text) {
        //判断是否有参数
        if (jp.getArgs() == null) {
            return;
        }
        //获取方法名
        String methodName = jp.getSignature().getName();
        SysOperationLog log = new SysOperationLog();
        log.setCreateTime(new Date());
        log.setMethod(methodName);
        log.setOperation(getDesc(jp));
        log.setArgs(adminOptionContent(jp.getArgs()));
        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        log.setUserName(currentUser.getUsername());
        log.setUserId(currentUser.getId());
        operationLogService.insert(log);
    }


    /**
     * 记录异常
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterException(JoinPoint joinPoint, Exception e) {
        System.out.print("-----------afterException:" + e.getMessage());
        addLog(joinPoint, getDesc(joinPoint) + e.getMessage());
    }


    private String getDesc(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).desc();
    }

    private Log.LOG_TYPE getType(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).type();
    }

    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     */
    public String adminOptionContent(Object[] obj) {
        if (obj == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                buffer.append("[参数" + (i + 1) + ":");
                Object o = obj[i];
                if (o instanceof Model) {
                    continue;
                }
                String parameter = null;
                try {
                    parameter = JSON.toJSONString(o);
                } catch (Exception e) {
                    continue;
                }
                buffer.append(parameter);
                buffer.append("]");
            }
        }
        return buffer.toString();
    }
}
