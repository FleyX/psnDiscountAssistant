package com.fanxb.backservice.exception;

import com.fanxb.backservice.entity.output.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/19 18:12
 */
@RestControllerAdvice
public class ExceptionHandle {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        logger.error("捕获到错误:", e);
        if (e instanceof CustomException) {
            return Result.failed(e.getMessage());
        } else {
            return Result.failed("");
        }
    }
}
