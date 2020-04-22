package com.northwind.catalog.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Configuration
@Aspect
public class SecuredAspect {

    @Before("@annotation(com.northwind.catalog.config.Secured)")
    public void validateTokent(JoinPoint joinPoint) throws IllegalAccessException {
        HttpServletRequest request = getRequest();
        String authToken = request.getHeader("X-AUTH_TOKEN");
        System.out.println(authToken);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Secured secured = method.getAnnotation(Secured.class);

        if((secured.equals("read"))){
            if (authToken == null || !authToken.equals("ABC")) {
                throw new IllegalAccessException();
            }
        }else if((secured.value().equals("write"))){
            if (authToken == null || !authToken.equals("XYZ")) {
                throw new IllegalAccessException();
            }
        }

    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }
}
