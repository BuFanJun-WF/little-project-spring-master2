package com.wangfan.little.project.spring.controller;

import com.ruyuan.little.project.common.dto.CommonResponse;
import com.wangfan.little.project.spring.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangfan
 * @version 1.0.0
 * @date 2022/7/23 17:46
 * @description 进行健康检查的controller
 */

@RestController
public class HealthController {

    @RequestMapping("/")
    public CommonResponse health() {

        //return CommonResponse.success();

        throw new BusinessException("系统异常，请联系管理员");
    }
}
