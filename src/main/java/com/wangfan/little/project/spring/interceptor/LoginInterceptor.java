package com.wangfan.little.project.spring.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ruyuan.little.project.common.dto.CommonResponse;
import com.wangfan.little.project.spring.constants.StringPoolConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wangfan
 * @version 1.0.0
 * @date 2022/7/24 15:52
 * @description 用来处理登录请求之前的判断工作。判断是否登录拦截器，前端会在发送请求时传递手机号码phoneNumber
 *              这个拦截类会配置在xml文件中，进行启动激活
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 判断请求中是否包含手机号，先中请求体中获取
        String phoneNumber = request.getParameter("phoneNumber");
        // 获取得到为空时
        if (StringUtils.isEmpty("phoneNumber")) {
            // 再一次从请求头中获取
            phoneNumber = request.getHeader("phoneNumber");
            if (StringUtils.isEmpty(phoneNumber)) {
                LOGGER.info("请求接口： {} 中不包含手机号",request.getRequestURI());

                // 获取OutputStream输出流
                try (OutputStream outputStream = response.getOutputStream()) {
                    //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
                    response.setHeader("content-type", "application/json");
                    //将字符CommonResponse.fail()转换成字节数组，指定以UTF-8编码进行转换
                    byte[] dataByteArr = JSONObject.toJSONString(CommonResponse.fail()).getBytes(StringPoolConstant.UTF8);
                    //使用OutputStream流向客户端输出字节数组
                    outputStream.write(dataByteArr);
                } catch (IOException e) {
                    LOGGER.error("LoginInterceptor exception",e);
                }
                return false;
            }
        }

        // 进行日志输出
        LOGGER.debug("LoginInterceptor.preHandle()");
        // 能获取得到手机号
        return true;
    }

}
