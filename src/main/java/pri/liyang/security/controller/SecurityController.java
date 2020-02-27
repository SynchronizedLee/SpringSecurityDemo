package pri.liyang.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LiYang
 * @Date: 2020/2/25 16:53
 * @Description: Spring Security 功能控制器
 */
@RestController
public class SecurityController {

    //请求缓存工具类：拿到引发跳转的请求（被判定需要登录的请求）
    private RequestCache requestCache = new HttpSessionRequestCache();

    //引发重定向的工具类，实现Redirect
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    /**
     * 当需要身份认证时，跳转到这里，可以根据需要决定（本例是以.html结尾
     * 的请求，都重定向到登录页/userLogin.html，否则就返回JSON）：
     *     1、重定向请求到指定页面（登录页）
     *     2、返回JSON，可设置状态码401(Unauthorized)，前端拿到该状态码
     * 后统一处理，比如跳转到登录页。适用于前后端分离的后端项目
     *
     * @param request 请求体
     * @param response 响应体
     * @return
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Object requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //从请求缓存中，拿到引发跳转的那个请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        //如果有这个请求
        if (savedRequest != null) {

            //获得引发跳转的请求的URL
            String targetUrl = savedRequest.getRedirectUrl();

            //如果是以.html结尾的请求
            if (targetUrl.endsWith(".html")) {

                //登录页的URL（这里可以引入配置，不写死）
                String loginPage = "/userLogin.html";

                //跳转到登录页
                redirectStrategy.sendRedirect(request, response, loginPage);
            }
        }

        //如果走到了这里，证明不是.html的请求，返回JSON
        //这种适合于前后端分离的后段项目

        //简单制作响应信息
        Map<String, Object> res = new HashMap<>();
        res.put("code", 401);
        res.put("message", "访问的服务需要身份验证，请引导用户到登录页");

        //返回未登录的提示信息JSON
        return res;
    }

}
