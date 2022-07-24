package com.wangfan.little.project.spring.exception;

/**
 * @author wangfan
 * @version 1.0.0
 * @date 2022/7/23 22:36
 * @description 系统内部异常
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = -994962710559017255L;

    public BusinessException(String message) {
        super(message);
    }
}
