package com.cy.springboot.service.impl;

import com.cy.springboot.entity.User;
import com.cy.springboot.mapper.UserMapper;
import com.cy.springboot.service.IUserService;
import com.cy.springboot.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        //通过user参数来获取传过来的username
        String username = user.getUsername();
        //判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        //判断结果集是否为null，不为null则抛出用户名被占用异常
        if (result!=null){
            throw new UsernameDuplicatedException("用户名被占用");
        }
        //密码加密处理的实现：md5算法的形式
        //串+password+串 ---md5算法进行加密，连续加密三次
        //盐值+password+盐值 ---盐值就是一个随机的字符串

        //随机生成一个盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        Date date = new Date();
        //将密码和盐值作为一个整体进行加密，忽略原有密码强度提升了数据安全
        String md5Password = getMd5Password(user.getPassword(), salt);
        //将加密后的密码补全到user对象中
        user.setPassword(md5Password);
        //补全数据：isDelete设置成0
        user.setSalt(salt);
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现
        Integer rows = userMapper.insert(user);
        if (rows!=1){
            throw new InsertException("用户注册时产生了未知的异常，请联系管理员！");
        }
    }

    /**
     * 用户登录功能
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if (result==null){
            throw new UserNotFoundException("用户数据不存在");
        }
        if (result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(password, salt);
        System.out.println("加密后的密码："+md5Password);
        System.out.println("数据库中的的密码："+result.getPassword());
        if (!result.getPassword().equals(md5Password)){
            throw new PasswordNotMatchException("密码验证失败");
        }
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result==null){
            throw new UserNotFoundException("用户数据不存在");
        }
        if (result.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户数据不存在");
        }
        String salt = result.getSalt();
        String oldMd5Password = getMd5Password(oldPassword, salt);
        if (!result.getPassword().contentEquals(oldMd5Password)){
            throw new PasswordNotMatchException("原密码错误");
        }
        String newMd5Password = getMd5Password(newPassword, salt);
        Date now = new Date();
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, now);
        if (rows!=1){
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建新的User对象
        User user = new User();
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        // 返回新的User对象
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        if (result==null){
            throw new UserNotFoundException("用户数据不存在");
        }


        // 判断查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常
        if (result.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户数据不存在");
        }
        // 向参数user中补全数据：uid
        user.setUid(uid);
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows = userMapper.updateInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        // 是：抛出UpdateException异常
        if (rows!=1){
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 检查查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检查查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updateAvatarByUid()方法执行更新，并获取返回值
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, now);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * MD5算法加密
     * @return 返回加密后的字符串
     */
    public String getMd5Password(String password,String salt){
        //三次加密
        for (int i = 0; i < 3; i++) {
            password=DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        //返回加密之后的密码
        return password;
    }
}
