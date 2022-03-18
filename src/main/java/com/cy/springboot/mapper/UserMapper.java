package com.cy.springboot.mapper;

import com.cy.springboot.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//用户模块的持久层接口
public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return 受影响的行数（增、删、改都受影响的行数作为返回值，可以根据返回值来判断是否执行成功）
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 如果找到对应的用户则返回这个用户的数据，如果没有找到则返回null
     */
    User findByUsername(String username);

    /**
     * 根据uid修改用户密码
     * @param uid 用户的id
     * @param password 新密码
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return
     */
    Integer updatePasswordByUid(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id查询用户数据
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的用户数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据uid更新用户信息
     * @param user 用户的数据
     * @return 返回受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @Param("SQL映射文件中#{}占位符的变量名")
     * 解决问题：当SQL语句的占位符和映射文件的接口方法参数名不一致时，需要将某个参数强行注入到某个占位符变量上
     * 根据用户的uid修改用户头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
