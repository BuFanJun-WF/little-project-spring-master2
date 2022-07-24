package com.wangfan.little.project.spring.handler;

import com.ruyuan.little.project.common.dto.CommonResponse;
import com.wangfan.little.project.spring.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangfan
 * @version 1.0.0
 * @date 2022/7/24 15:30
 * @description 添加一个全局异常处理类
 *              其中@ControllerAdvice表示这是一个增强型的controller，对controller的请求进行增强，像AOP一样，进行功能织入增强
 *              可以实现三个方面的功能：全局异常处理、全局数据绑定和全局数据预处理。
 *              这里@ResponseBody表示Controller方法返回结果直接写入HTTP响应正文（ResponseBody）中。可以将controller的方法返回的对象，
 *              通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 这里的@ExceptionHandler的参数标注的是一个Exception.class，表示用来处理Exception的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResponse handleException(Exception e) {
        LOGGER.error("系统异常错误信息:{}", e);
        return CommonResponse.fail();
    }

    @ExceptionHandler(value = BusinessException.class)
    public CommonResponse handleBusinessException(BusinessException e) {
        LOGGER.error("系统异常错误信息:{}", e);
        return CommonResponse.fail();
    }


}
