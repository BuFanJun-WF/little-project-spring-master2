package com.wangfan.little.project.spring.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruyuan.little.project.common.dto.CommonResponse;
import com.wangfan.little.project.spring.constants.StringPoolConstant;
import com.wangfan.little.project.spring.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
/**
 * @author wangfan
 * @version 1.0.0
 * @date 2022/7/23 18:37
 * @description 定义日志AOP中的切面，其中包含了advice（加入早切入点的增强功能）和pointcut（选择增强的切入点）
 *              也就是实现了controller层日志拦截组件
 *              表示切面的组件（@Aspect）
 *              表示注册为组件bean（@Component）
 */
@Aspect
@Component
public class ControllerLogAspect {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogAspect.class);

    /**
     * 定义一个切入点
     * 指明了在com.wangfan.little.project.spring.controller的命名空间以内的类文件都要
     * 进行日志拦截
     */
    @Pointcut("execution(* com.wangfan.little.project.spring.controller.*.*(..))")
    public void pointcut() {}

    /**
     * 环绕通知，在方法执行前后 @Around("pointcut()")这个注解所表示的意思，增强的内容
     * @param point 切入点，指的是需要增强的方法叫做point，也就是Controller中的需要记录日志的方法
     * @return 结果
     * @throws BusinessException
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws BusinessException {
        // 签名信息
        Signature signature = point.getSignature();
        // 强转为方法信息
        MethodSignature methodSignature = (MethodSignature) signature;
        // 获取方法的参数名称
        String[] parameterNames = methodSignature.getParameterNames();
        // 获取需要执行的方法对象
        Object target = point.getTarget();

        // 通过info方法进行“记录处理方法”和“请求处理参数”
        LOGGER.info("请求处理方法：{}", target.getClass().getName() + StringPoolConstant.DOT + methodSignature.getMethod().getName());
        Object[] parameterValues = point.getArgs();
        // 查看入参
        LOGGER.info("请求参数名:{}，请求参数值:{}", JSONObject.toJSONString(parameterNames), JSONObject.toJSONString(parameterValues));

        try {
            // 开始时间
            long startTime = System.currentTimeMillis();
            // 执行方法，上面和下面的代码都是对这个point（方法）执行的增强
            Object response = point.proceed();
            // 结束时间
            long endTime = System.currentTimeMillis();
            LOGGER.info("请求处理时间差:{}, 响应结果:{}", endTime - startTime, JSON.toJSONString(response));
            return response;
        } catch (Throwable throwable) {
            LOGGER.error("执行方法:{}失败，异常信息:{}", methodSignature.getMethod().getName(), throwable);
            // 如果出现了业务异常
            if (throwable instanceof BusinessException) {
                return CommonResponse.fail();
            }
            // 非业务异常，封装一层异常
            throw new BusinessException("方法 " + methodSignature.getMethod().getName() + " 执行失败");
        }

    }


}
