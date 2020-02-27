package pri.liyang.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LiYang
 * @Date: 2020/2/26 21:50
 * @Description: 自定义的登录成功处理方案实现类
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    //为我们提供对象转JSON功能的工具
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 重写登录成功的处理方法。设置后，当用户登录成功后会调用下面的方法
     * @param request 请求体
     * @param response 响应体
     * @param authentication Security核心接口，封装了登录用户的认证信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //将返回的内容，设置为JSON格式
        response.setContentType("application/json;charset=UTF-8");

        //获取用户的登录认证信息，然后返回前端，以JSON字符串的形式展现
        //你可以根据request、response、以及登录用户的认证信息authentication干任何你想干的事情
        //当然，这里也可以配置页面跳转，根据需要来进行
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }

}
