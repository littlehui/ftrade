package com.littlehui.ftrade.web.handler;

import com.littlehui.ftrade.dto.web.Response;
import com.u17173.treasurebox.auth.token.exception.AuthzException;
import com.u17173.treasurebox.auth.token.exception.InvalidTokenException;
import com.u17173.treasurebox.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;


/**
 * @author wangj on 2018/3/9.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value=BusinessException.class)
    @ResponseBody
    public ResponseEntity<Response<String>> handleServiceException(Exception exception) {
        logger.error("业务异常!", exception);
        return new ResponseEntity<>(Response.getFailedResponse(exception.getMessage()), getHttpHeaders(), HttpStatus.OK);
    }
    @ExceptionHandler(value=InvalidTokenException.class)
    @ResponseBody
    public ResponseEntity<Response<String>> handleTokenException(InvalidTokenException exception) {
        return new ResponseEntity<>(Response.getFailedResponse(exception.getMessage()), getHttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value=AuthzException.class)
    @ResponseBody
    public ResponseEntity<Response<String>> handleTokenException(AuthzException exception) {
        return new ResponseEntity<>(Response.getFailedResponse(exception.getMessage()), getHttpHeaders(), HttpStatus.FORBIDDEN);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        return httpHeaders;
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public ResponseEntity<Response<String>> handleException(Exception exception) {
        logger.error("系统异常!", exception);
        return new ResponseEntity<>(Response.getFailedResponse("操作失败，请联系管理员！"), getHttpHeaders(), HttpStatus.OK);
    }

    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Response<String>> handleException(MethodArgumentNotValidException exception) {
        logger.error("参数错误!", exception);
        if (exception.getBindingResult().getFieldError() != null) {
            String errorMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
            return new ResponseEntity<>(Response.getFailedResponse(errorMessage), getHttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Response.getFailedResponse("参数错误。"), getHttpHeaders(), HttpStatus.OK);
        }
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<Response<String>> handle(ValidationException exception) {
        StringBuffer errorMessageBuffer = new StringBuffer();
        if(exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                /**打印验证不通过的信息*/
                logger.error(item.getMessage());
                errorMessageBuffer.append(item.getMessage());
            }
        }
        return new ResponseEntity<>(Response.getFailedResponse(errorMessageBuffer.toString()), getHttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value=MultipartException.class)
    @ResponseBody
    public ResponseEntity<Response<String>> handleException(MultipartException exception) {
        logger.error("参数错误!", exception);
        return new ResponseEntity<>(Response.getFailedResponse("上传文件错误,最大5M"), getHttpHeaders(), HttpStatus.OK);
    }
}
