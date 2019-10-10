package com.yichen.config;

import com.yichen.properties.ApiProperties;
import com.yichen.request.AbstractParam;
import com.yichen.request.RequestHolder;
import com.yichen.util.AspectUtil;
import com.yichen.util.JsonUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 基础接口切面
 * <p>
 * 填充基础用户等，必须保证最高优先级
 * @author dengbojing
 */
@Slf4j
@Aspect
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureAfter(CommonConfiguration.class)
@EnableConfigurationProperties({ApiProperties.class})
@ConditionalOnProperty(prefix = ApiProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class ApiAspectAutoConfigure {

    @Resource
    private ApiProperties apiProperties;

    @Resource
    private JsonUtil jsonHelper;

    private volatile Map<String, AtomicInteger> apiVisits = new ConcurrentHashMap<>();

    private volatile AtomicLong totalVisit = new AtomicLong(0);

    /**
     * API接口调用切面配置
     */
    @Pointcut("execution(com.yichen.response.CommonResponse com.yichen..api..*Api.*(..))")
    public void executeForAPI() {
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint 切点信息
     * @return object
     * @throws Throwable ...
     */
    @Around("executeForAPI()")
    public Object aroundExecuteForAPI(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        val requestId = UUID.randomUUID().toString().replace("-", "");
        logRequest(requestId, proceedingJoinPoint);
        val start = System.currentTimeMillis();
        val requestAttributes = RequestContextHolder.getRequestAttributes();
        val args = proceedingJoinPoint.getArgs();
        if (requestAttributes == null || AspectUtil.hasFileArg(args)) {
            return proceedingJoinPoint.proceed();
        }
        val requestInfo = RequestHolder.info();
        if (!"POST".equals(requestInfo.getMethod())) {
            return proceedingJoinPoint.proceed();
        }
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof AbstractParam) {
                    ((AbstractParam) arg).setUserId(requestInfo.getUser().getUserId());
                    ((AbstractParam) arg).setOrganizationId(requestInfo.getUser().getOrganizationId());
                    break;
                }
            }
        }
        if (apiProperties.getLog()) {
            log.info(new SimpleRequest(requestInfo.getPath(), args).string());
        }

        Object body = proceedingJoinPoint.proceed(args);
        // 请求时间
        val time = (System.currentTimeMillis() - start);
        // 记录请求次数
        totalVisit.incrementAndGet();
        if (!apiVisits.containsKey(requestInfo.getPath())) {
            apiVisits.put(requestInfo.getPath(), new AtomicInteger(0));
        } else {
            apiVisits.get(requestInfo.getPath()).incrementAndGet();
        }
        // 延迟较高的请求log
        if (time > apiProperties.getMs()) {
            log.warn(String.format("%-3sms %-52s", time, requestInfo.getPath()));
        }
        // 每指定次数打印log
        if (totalVisit.get() % apiProperties.getStep() == 0L) {
            log.info("总请求次数：" + totalVisit.get());
            log.info(jsonHelper.string(apiVisits));
        }
        logResponse(requestId, time, body);
        return body;
    }

    private void logRequest(String requestId, ProceedingJoinPoint proceedingJoinPoint) {
        val logInfo = new HashMap<String, Object>();
        logInfo.put("requestId", requestId);
        logInfo.put("logType", "request");
        val requestInfo = RequestHolder.info();
        logInfo.put("path", requestInfo.getPath());
        logInfo.put("method", requestInfo.getMethod());
        logInfo.put("from", requestInfo.getFrom());
        logInfo.put("host", requestInfo.getHost());
        logInfo.put("userId", requestInfo.getUser().getUserId());
        List<Object> params = new ArrayList<>();
        val args = proceedingJoinPoint.getArgs();
        if (null != args) {
            for (Object arg : args) {
                if (!(arg instanceof MultipartFile || arg instanceof HttpServletRequest)) {
                    params.add(arg);
                }
            }
        }
        logInfo.put("params", params);
        log.info(jsonHelper.string(logInfo));
    }

    private void logResponse(String requestId, long time, Object body) {
        val logInfo = new HashMap<String, Object>();
        logInfo.put("logType", "response");
        logInfo.put("requestId", requestId);
        logInfo.put("time", time);
        logInfo.put("body", body);
        log.info(jsonHelper.string(logInfo));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class SimpleRequest {
        private String url;
        private Object param;

        private String string() {
            return jsonHelper.string(this);
        }
    }

}
