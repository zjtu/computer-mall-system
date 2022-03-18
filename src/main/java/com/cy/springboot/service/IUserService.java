package com.cy.springboot.service;

import com.cy.springboot.entity.User;

public interface IUserService {
     /**
      * 用户注册方法
      * @param user 用户的数据对象
      */
     void reg(User user);

     /**
      * 用户登录方法
      * @param username 用户名
      * @param password 用户密码
      * @return
      */
     User login(String username,String password);

     /**
      * 用户修改密码
      * @param uid 当前登录的用户uid
      * @param username 当前登录的用户名
      * @param oldPassword 原密码
      * @param newPassword 新密码
      */
     void changePassword(Integer uid, String username, String oldPassword, String newPassword);

     /**
      * 获取当前登录的用户的信息
      * @param uid 当前登录的用户的uid
      * @return 当前登录的用户的信息
      */
     User getByUid(Integer uid);
     /**
      * 修改用户资料
      * @param uid 当前登录用户的uid
      * @param username 当前登录的用户名
      * @param user 用户的新的数据
      */
     void changeInfo(Integer uid, String username, User user);

     /**
      * 修改用户头像
      * @param uid 当前登录的用户的id
      * @param username 当前登录的用户名
      * @param avatar 用户的新头像的路径
      */
     void changeAvatar(Integer uid,String username,String avatar);
}
