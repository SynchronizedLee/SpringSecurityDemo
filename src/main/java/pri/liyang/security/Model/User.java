package pri.liyang.security.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: LiYang
 * @Date: 2020/2/25 12:54
 * @Description: 实现了UserDetails的User类，处理用户校验逻辑
 */
public class User implements UserDetails {

    /**
     * 自定义Field，这些Field值将在实现了UserDetailsService接口的
     * 组件类中的loadUserByUsername(String username)方法中赋值。
     * username来源于用户登录提交的表单中的用户名字段，通过这个
     * 字段从数据库中查询用户的所有信息，然后构建这些Field。这些
     * Field都有了相应的值了之后，就靠这些值来实现UserDetails接口
     * 的所有方法，用作用户的鉴权
     */
    //用户名
    private String username;

    //密码
    private String password;

    //年龄
    private int age;

    //地址
    private String address;


    /************** 根据上述的Field值，实现以下接口方法，用作用户登录鉴权 **************/

    /**
     * 实现获取登录用户的权限集合（记得加前缀ROLE_）
     * @return 登录用户权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //可以单独定义权限（字符串，也就是ROLE），然后装集合
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_admin");

        //也可以直接调用Spring工具类，生成权限集（入参单个，或逗号分隔的多个字符串）
        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_super,ROLE_admin");

        //以下是自定义用户权限列表，作为Demo就按照前缀来区分，真实情况需要根据业务来定义
        //如果用户名以admin_开头，就具有admin权限
        if (username.startsWith("admin_")) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");

        //如果用户名以super_开头，就具有super权限
        } else if (username.startsWith("super_")) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_super");

        //如果用户名以ultra_开头，就同时具有super和admin权限
        } else if (username.startsWith("ultra_")) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_super,ROLE_admin");

        //否则，就只具有user权限
        } else {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user");
        }
    }

    /**
     * 重要：实现获取登录用户的密码，用来与登录输入的密码进行匹配
     * @return 登录用户的密码
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * 实现获取登录用户的用户名
     * @return 登录用户的用户名
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 实现获取用户登录账号是否没有过期
     * @return 账号是否没有过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 实现获取用户登录账号是否没有被锁定
     * @return 账号是否没有被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 实现获取用户登录密码是否没有过期
     * @return 账号密码是否没有过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 实现获取用户登录账号是否可用
     * @return 账号是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    /************** 以下是生成的Getter和Setter方法 **************/

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
