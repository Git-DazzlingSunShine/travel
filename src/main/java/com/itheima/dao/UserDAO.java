package com.itheima.dao;

import com.itheima.daomain.User;

import java.util.List;

/**
 * 用户DAO层
 */
public interface UserDAO {

    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return 用户实体
     */
    List<User> queryByEmail(String email);

    /**
     * 插入用户数据
     * @param user
     * @return 是否插入成功
     */
    int insert(User user);

    /**
     * 根据激活码参数将用户的激活状态置为1
     * @return
     * @param code
     */
    int active(String code);

    /**
     * 查询用户信息
     * @return
     * @param email
     * @param password
     */
    List<User> queryByEmailAndPassword(String email, String password);

}
