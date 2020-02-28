package pri.liyang.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.liyang.security.model.User;

/**
 * @Author: LiYang
 * @Date: 2020/2/24 22:53
 * @Description: 项目业务控制器
 */
@RestController
public class BusinessController {

    /**
     * 不需要登录就可以访问的公共信息页面
     * @return
     */
    @GetMapping("/public/info")
    public Object publicInfo() {
        //返回说明信息
        return "acquire more info after login";
    }

    /**
     * 获取当前登录用户信息，为登录者打招呼
     * 只要登录，用户都能访问
     * @return
     */
    @GetMapping("/user/hello")
    public Object sayHello() {
        //获取当前登录用户信息
        //因为loadUserByUserName()方法里返回的是User对象（实现了UserDetails），
        //所以这里可以强转为User对象
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //获得当前登录用户的用户名，并返回打招呼语句
        return "hello, " + loginUser.getUsername();
    }

    /**
     * 只有admin或super用户才能访问的管理页面
     * @return
     */
    @GetMapping("/admin/manage")
    public Object adminManage() {
        //获取管理员信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //返回管理员登录成功信息
        return "welcome my administrator, " + loginUser.getUsername();
    }

    /**
     * 只有super用户才能访问的超级管理员页面
     * @return
     */
    @GetMapping("/super/manage")
    public Object superAdminManage() {
        //获取超级管理员信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //返回管理员登录成功信息
        return "welcome my super administrator, " + loginUser.getUsername();
    }

    /**
     * 只有同时具备super和admin两项权限才能访问终极管理员页面
     * @return
     */
    @GetMapping("/ultra/manage")
    public Object ultraManage() {
        //获取终极管理员信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //返回终极管理员登录成功信息
        return "welcome my ultra administrator, " + loginUser.getUsername();
    }

    /**
     * 任何人都无法访问的秘密页面
     * @return
     */
    @GetMapping("/secret/info")
    public Object secretInfo() {
        return "such a secret you had seen, are you a hacker?";
    }

}
