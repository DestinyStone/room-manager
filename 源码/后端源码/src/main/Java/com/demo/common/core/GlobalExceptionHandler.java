package com.demo.common.core;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author zhou xiao fen
 * @Date 2023/8/31 22:35
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public R handlerServiceException(ServiceException e) {
        e.printStackTrace();
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e) {
        e.printStackTrace();
        return R.fail("未知异常");
    }
}
