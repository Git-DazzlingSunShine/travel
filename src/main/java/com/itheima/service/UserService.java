package com.itheima.service;

import com.itheima.daomain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 业务层，用户数据处理
 */
public interface UserService {
    /**
     * 查询邮箱是否存在
     * @param email
     * @return 是否存在结果，存在true，不存在false
     */
    boolean existEmail(String email);

    /**
     * 用户注册验证码校验
     * @param request
     * @param captcha
     * @return
     */
    boolean capctchaValid(HttpServletRequest request, String captcha);

    /**
     * 向数据库插入数据
     * 插入之前会生成激活码
     * 插入之后会自动发送邮件
     * @param parameterMap 表单数据
     * @return 如果插入成功的话，返回true；如果插入失败的话，返回false
     */
    boolean insert(Map<String, String[]> parameterMap);

    /**
     * 激活账号，传入code激活码，返回激活是否成功
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 查询用户信息
     * @return用户实体
     * @param email
     * @param password
     */
    User queryByEmailAndPassword(String email, String password);

    /**
     * 查询用账号是否激活
     * @return
     * @param user
     */
    boolean activeValid(User user);
}
