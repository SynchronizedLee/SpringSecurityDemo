package pri.liyang.security.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pri.liyang.security.model.User;

import java.util.Random;

/**
 * @Author: LiYang
 * @Date: 2020/2/25 12:52
 * @Description: 这里处理用户信息的获取逻辑
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    /**
     * 重写UserDetailsService的方法，定义用户的校验逻辑
     * @param s 登录传进来的用户名
     * @return 带登录用户信息的实现了UserDetails的实例
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //TODO 通过username，从数据库获取到用户的所有信息
        //从数据库里面通过用户名查询到的用户密码（实际情况应该是直接取出加密后的密码）
        //这里暂时设置用户账号密码就是：用户名 + "123"
        String password = new BCryptPasswordEncoder().encode(s + "123");

        //随机数类
        Random random = new Random();

        //地名集合
        String[] city = new String[]{"Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Chengdu"};

        //从数据库里面查出的用户其他信息
        //随机年龄，10-99岁
        int age = 10 + random.nextInt(90);
        //随机地名，北上广深蓉五选一
        String address = city[random.nextInt(city.length)];

        //然后封装好从数据库获取的用户信息为实现了UserDetails接口的自定义User类
        User user = new User();
        user.setUsername(s);
        user.setPassword(password);
        user.setAge(age);
        user.setAddress(address);

        //返回该User类，系统会从该User类中调用方法，获取该用户
        //的用户名、密码、权限列表，以及其他信息，用作鉴权
        return user;
    }

}
