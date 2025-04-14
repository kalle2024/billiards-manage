package cn.hy.config;

import cn.dev33.satoken.util.SaResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常配置
 *
 * @author hy
 */
@RestControllerAdvice
public class GlobalExceptionConfig {

    @ExceptionHandler(value = Exception.class)
    public SaResult exceptionHandler(BindException e) {
        return SaResult.error(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public SaResult exceptionMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null) {
            return SaResult.error(fieldError.getDefaultMessage());
        }
        return SaResult.error(e.getMessage());
    }
}
