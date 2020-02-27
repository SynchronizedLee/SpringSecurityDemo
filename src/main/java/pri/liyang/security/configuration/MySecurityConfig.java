package pri.liyang.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * @Author: LiYang
 * @Date: 2020/2/25 13:19
 * @Description: 这里是SpringSecurity的Web端配置类
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 这里需要提供一个密码编码器
     * @return 密码编码器的Bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //直接返回Spring自带的密码编码器
        return new BCryptPasswordEncoder();

        //TODO 也可以自己实现密码编辑器
        /*return new PasswordEncoder() {*/

            /**
             * 这里定义你的密码加密方式
             * @param charSequence 原始密码
             * @return 加密后的密码
             */
            /*@Override
            public String encode(CharSequence charSequence) {
                return null;
            }*/

            /**
             * 这里定义你的密码匹配方式
             * @param charSequence 输入的密码
             * @param s 用户账号的密码
             * @return 是否匹配
             */
            /*@Override
            public boolean matches(CharSequence charSequence, String s) {
                return false;
            }*/
        /*};*/
    }

    //注入自己实现的登陆成功处理器类
    @Resource
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    //注入自己实现的登录失败处理器类
    @Resource
    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    //注入自己实现的登出成功处理器类
    @Resource
    private LogoutSuccessHandler myLogoutSuccessHandler;

    /**
     * Spring Security：HTTP请求安全处理
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //请求的权限配置(哪些人能访问哪些请求)

                .antMatchers(
                        "/userLogin.html",   //添加匹配器，匹配登录页面
                        "/authentication/require"   //添加匹配器，匹配身份验证控制器映射
                ).permitAll()   //以上的请求均不需要验证

                //只要具备super或admin任一权限的用户，就能访问以/admin开头的URL
                .antMatchers("/admin/**").hasAnyRole("super", "admin")

                //只有super用户才能访问以/super开头的URL
                .antMatchers("/super/**").hasRole("super")

                //只有同时具备super和admin两重身份的用户，才能访问以/ultra开头的URL
                .antMatchers("/ultra/**").access("hasRole('super') and hasRole('admin')")

                //任何人都可以访问以/public开头的URL
                .antMatchers("/public/**").permitAll()

                //任何人都不能访问以/secret开头的URL
                .antMatchers("/secret/**").denyAll()

                .anyRequest()   //其他的所有请求
                .authenticated()   //都需要鉴权

                .and()
                .formLogin()   //配置登录方式为表单登录

                //.loginPage("/userLogin.html")   //定义登录页面所在的URL(如果有登录页面的话)
                .loginPage("/authentication/require")   //定义身份验证控制器映射(可灵活处理是跳转登录页，还是返回JSON)
                .loginProcessingUrl("/authentication/login")   //定义登录表单提交的URL，不配置的话默认是/login，方法都是Post

                .usernameParameter("username")   //定义用户名的表单字段名称，默认为username
                .passwordParameter("password")   //定义密码的表单字段名称，默认为password

                //配置自定义登录成功处理器，用户登录成功后执行里面的onAuthenticationSuccess()方法
                //这个的优先级高，会覆盖Spring Security默认的处理方式
                .successHandler(myAuthenticationSuccessHandler)

                //配置自定义登录失败处理器，用户登录失败后执行里面的onAuthenticationFailure()方法
                //与登录成功的处理器一样，这个的优先级高，会覆盖Spring Security默认的处理方式
                .failureHandler(myAuthenticationFailureHandler)

                .and()
                .logout()   //配置用户登出
                .logoutUrl("/authentication/logout")   //用户登出的URL

                //配置自定义登出成功处理器，用户登出成功后执行里面的onLogoutSuccess()方法
                //与登录成功的处理器一样，包含了request、response和用户认证信息authentication
                .logoutSuccessHandler(myLogoutSuccessHandler)

                .and()
                .csrf().disable();   //关闭跨站请求csrf
    }

}
