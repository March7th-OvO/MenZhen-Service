package com.oms.menzhenservice.exception;

import com.oms.menzhenservice.common.Result;
import lombok.extern.slf4j.Slf4j; // 需要 Lombok
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获业务逻辑抛出的 RuntimeException
     * 例如：库存不足、患者已存在等
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 捕获所有未知的系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(500, "系统内部错误，请联系管理员");
    }
}