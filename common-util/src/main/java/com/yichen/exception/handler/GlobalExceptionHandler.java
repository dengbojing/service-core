package com.yichen.exception.handler;

import com.yichen.exception.BusinessException;
import com.yichen.properties.ExceptionProperties;
import com.yichen.response.CommonResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author  dengbojing
 * 统一异常处理
 */

@Slf4j
@Configuration
@ControllerAdvice
@Setter
@EnableConfigurationProperties({ExceptionProperties.class})
public class GlobalExceptionHandler {

    @Resource
    private ExceptionProperties globalExceptionProperties;


    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommonResponse handle(BusinessException throwable) throws Throwable {
        log.error("BusinessException：" + throwable.getMessage());
        deal(throwable);
        val response = new CommonResponse<>();
        response.setCode(throwable.getCode());
        response.setMessage(throwable.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse handle(MethodArgumentNotValidException throwable) throws Throwable {
        log.error("MethodArgumentNotValidException：" + throwable.getMessage());
        deal(throwable);
        if (null != throwable.getBindingResult().getFieldError()) {
            return CommonResponse.failed(throwable.getBindingResult().getFieldError().getDefaultMessage());
        }
        return CommonResponse.failed(throwable.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public CommonResponse handle(HttpMessageNotReadableException throwable) throws Throwable {
        log.error("HttpMessageNotReadableException：" + throwable.getMessage());
        deal(throwable);
        return CommonResponse.failed("参数格式错误");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public CommonResponse handle(RuntimeException throwable) throws Throwable {
        log.error("RuntimeException：" + throwable.getMessage());
        deal(throwable);
        return CommonResponse.failed(throwable.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse handle(Exception throwable) throws Throwable {
        log.error("Exception：" + throwable.getMessage());
        deal(throwable);
        return CommonResponse.failed(throwable.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public CommonResponse handle(Throwable throwable) throws Throwable {
        log.error("统一异常处理 Throwable：" + throwable.getMessage());
        deal(throwable);
        return CommonResponse.failed(throwable.getMessage());
    }

    /**
     * 处理异常
     *
     * @param throwable 异常
     * @throws Throwable 当前异常
     */


    private void deal(Throwable throwable) throws Throwable {
        if (globalExceptionProperties.getEnabled()) {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw, true));
            String logs = sw.toString();
            log.error(logs);
        } else {
            throw throwable;
        }
    }
}
