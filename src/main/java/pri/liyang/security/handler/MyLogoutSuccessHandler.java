package pri.liyang.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: LiYang
 * @Date: 2020/2/26 22:43
 * @Description: 自定义的用户登出成功处理方案实现类
 */
@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 重写登出成功的处理方法。设置后，当用户登出成功后会调用下面的方法
     * @param request 请求体
     * @param response 响应体
     * @param authentication 登录用户认证信息，有可能为空
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //将返回的内容，设置为文本或HTML格式
        response.setContentType("text/html;charset=UTF-8");

        //给前端写出一句话，表示该用户登出成功
        //你可以根据request、response、以及登录用户的认证信息authentication（可能为null）干任何你想干的事情
        //当然，这里也可以配置页面跳转，根据需要来进行
        response.getWriter().write("用户登出成功！登出时间：" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}
