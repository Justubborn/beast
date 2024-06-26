package cn.daben.beast.base;

import cn.daben.beast.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Justubborn
 * @since 2022/12/16
 */
@Slf4j
@RestControllerAdvice
public class BaseAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleException(NoHandlerFoundException e) {
        // 打印堆栈信息
        log.error(e.getMessage());
        return new ResponseEntity<>(RespEntity.fail(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public Object handleException(ApiException e) {
        // 打印堆栈信息
        log.error(e.getMessage());
        return RespEntity.fail(e.getMessage());
    }

    /**
     * 全局异常处理
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(Throwable.class)
    public Object handleException(Throwable e) {
        // 打印堆栈信息
        log.error(e.getMessage(), e);
        return RespEntity.fail(e.getMessage());
    }
}
