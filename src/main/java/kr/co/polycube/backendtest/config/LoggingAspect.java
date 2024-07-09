package kr.co.polycube.backendtest.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* kr.co.polycube.backendtest.controller.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void logUserAgent(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String userAgent = request.getHeader("User-Agent");
        log.info("User-Agent: {}", userAgent);
    }
}
