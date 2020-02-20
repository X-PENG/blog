package com.peng.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/

/**
 * 切面=切点+增强。
 */
@Aspect//使该类成为切面类
@Component//将切面类对象注册到IOC容器中
public class LogAspect {
    //具有默认值的实例变量
    private final Logger logger= LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.peng.blog.web..*.*(..))")
    public void log(){}


    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String method=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        RequestLog requestLog=new RequestLog(url,ip,method,joinPoint.getArgs());
        logger.info("RequestLog:{}",requestLog.toString());
    }
    @AfterReturning(value = "log()",returning = "result")
    public void doAfterReturning(Object result){
        logger.info("Return result:{}",result);
    }
//    @Before("log()")
//    public void doBefore(JoinPoint joinPoint){
//        logger.info("-------doBefore-----");
//        logger.info("目标方法：{}",joinPoint.getSignature().getName());
//        logger.info("目标方法所属类：{}",joinPoint.getSignature().getDeclaringType().getSimpleName());
//        logger.info("目标方法所属类的完全限定类名：{}",joinPoint.getSignature().getDeclaringTypeName());
//        logger.info("目标方法声明类型：{}", Modifier.toString(joinPoint.getSignature().getModifiers()));
//        Object[] args=joinPoint.getArgs();
//        for(int i=0;i<args.length;i++){
//            logger.info("第{}个参数为：{}",(i+1),args[i]);
//        }
//        logger.info("被代理对象：{}",joinPoint.getTarget());
//        logger.info("代理对象自己：{}",joinPoint.getThis());
//    }
//
//    @After("log()")
//    public void doFinal(){
//        logger.info("------doFinal--------");
//    }
//
//    @AfterReturning(value="log()",returning="result")
//    public void doAfterReturning(Object result){
//        logger.info("Result:{}",result.toString());
//    }
//
//    @Around("log()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint){
//        Object result=null;
//        logger.info("---环绕前置增强---");
//        try {
//            result=proceedingJoinPoint.proceed();
//            logger.info("result1:{}",result);
//            result=proceedingJoinPoint.proceed(new Object[]{"p",666,false});
//            logger.info("result2:{}",result);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        logger.info("---环绕后置通知---");
//        return result;
//    }

    //成员内部类。该类对象只会在本类中使用。将日志信息封装起来。
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog() {
        }

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
