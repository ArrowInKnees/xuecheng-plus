package com.xuecheng.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理器
 * @Author sdy
 * @Date 2023/8/18 9:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //  对项目的自定义异常类型进行处理
    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(XueChengPlusException e) {
        //  记录异常
        log.error("【系统异常】{}", e.getErrMessage(), e);
        //  解析异常并返回
        return new RestErrorResponse(e.getErrMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e) {
        //  记录异常
        log.error("【系统异常】{}", e.getMessage(), e);
        //  解析异常并返回
        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    //  MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        //  存储错误信息
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(item -> {
            errors.add(item.getDefaultMessage());
        });
        //  将List中的错误信息拼接起来
        String errMessage = StringUtils.join(errors, ",");
        //  记录异常
        log.error("【系统异常】{}", e.getMessage(), errMessage);
        //  解析异常并返回
        return new RestErrorResponse(errMessage);
    }
}
