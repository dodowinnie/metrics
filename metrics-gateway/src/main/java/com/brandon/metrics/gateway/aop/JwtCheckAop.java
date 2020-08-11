package com.brandon.metrics.gateway.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import com.brandon.metrics.gateway.dto.ReturnData;
import com.brandon.metrics.gateway.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

@Component
@Aspect
public class JwtCheckAop {
	
	private static Logger logger = LoggerFactory.getLogger(JwtCheckAop.class);

	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Pointcut("@annotation(com.brandon.metrics.gateway.annotation.JwtCheck)")
	private void apiAop() {};
	
	/**
	 * 环绕通知
	 * @param point
	 * @return
	 * @throws Throwable 
	 */
	@Around("apiAop()")
	public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		// 获取参数上的所有注解
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		Object[] args = point.getArgs();
		String token = null;
		for (Annotation[] annotations : parameterAnnotations) {
			for (Annotation annotation : annotations) {
				if (annotation instanceof RequestHeader) {
					RequestHeader requsetHeader = (RequestHeader) annotation;
					if ("Authorization".equals(requsetHeader.value())) {
						token = (String) args[ArrayUtils.indexOf(parameterAnnotations,annotations)];
					}
				}
				
			}
		}
		
		if (StringUtils.isBlank(token)) {
			return authErro("请登陆");
		}else{
            //有token
            try {
            	// 校验token
                JwtUtil.checkToken(token,objectMapper);
                Object proceed = point.proceed();
                return proceed;
            }catch (ExpiredJwtException e){
                logger.error(e.getMessage(),e);
                if(e.getMessage().contains("Allowed clock skew")){
                    return authErro("认证过期");
                }else{
                    return authErro("认证失败");
                }
            }catch (Exception e) {
                logger.error(e.getMessage(),e);
                return authErro("认证失败");
            }
		}
		
	}

	 /**
     * 认证错误输出
     * @param mess 错误信息
     * @return
     */
    private Object authErro(String mess) {
        ReturnData<String> returnData = new ReturnData<>(403, mess, mess);
        return returnData;
    }
}
