package pri.liyang.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LiYang
 * @Date: 2020/2/26 22:18
 * @Description: 自定义的登录失败处理方案实现类
 */
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    //为我们提供对象转JSON功能的工具
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 重写登录失败的处理方法。设置后，当用户登录失败后会调用下面的方法
     * @param request 请求体
     * @param response 响应体
     * @param ae 认证失败的异常信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae) throws IOException, ServletException {
        //登录失败，设置状态码为500，即服务器内部异常
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        //将返回的内容，设置为JSON格式
        response.setContentType("application/json;charset=UTF-8");

        //获取用户的登录失败异常信息，然后返回前端，以JSON字符串的形式展现
        //你可以根据request、response、以及登录用户的失败异常信息ae，干任何你想干的事情
        //当然，这里也可以配置页面跳转，根据需要来进行
        response.getWriter().write(objectMapper.writeValueAsString(ae));
    }

}
