package com.cy.springboot.mapper;

import com.cy.springboot.entity.Address;
import com.cy.springboot.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@SpringBootTest:表示当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith:表示启动这个单元测试类(单元测试不能运行)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {

    //userMapper报红：idea有检测功能，接口是不能直接创建bean的（动态代理技术解决）
    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试方法：可以单独独立运行，不需要启动整个项目，提升了代码的测试效率
     * 1.单元测试类必须被@Test注解修饰
     * 2.返回类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("test001");
        user.setPassword("123456");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("kite");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){
        Integer uid=11;
        String password="123456";
        String modifiedUser="超级管理员";
        Date modifiedTime = new Date();
        Integer rows=userMapper.updatePasswordByUid(uid,password,modifiedUser,modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUid(){
        Integer uid=12;
        User result = userMapper.findByUid(uid);
        System.out.println(result);
    }
    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(13);
        user.setPhone("17858802222");
        user.setEmail("admin@cy.com");
        user.setGender(1);
        user.setModifiedUser("系统管理员");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateAvatarByUid() {
        Integer uid = 13;
        String avatar = "/upload/avatar.png";
        String modifiedUser = "超级管理员";
        Date modifiedTime = new Date();
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void insertAddress(){
        Address address = new Address();

    }

}
