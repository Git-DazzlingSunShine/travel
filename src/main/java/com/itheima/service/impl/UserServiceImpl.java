package com.itheima.service.impl;

import com.itheima.dao.UserDAO;
import com.itheima.dao.impl.UserDAOImpl;
import com.itheima.daomain.User;
import com.itheima.service.UserService;
import com.itheima.utils.MailUtil;
import com.itheima.utils.UUIDUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.mail.EmailException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDAO dao = new UserDAOImpl();

    @Override
    public boolean existEmail(String email) {
        List<User> user = dao.queryByEmail(email);
        return user.size() == 1;
    }

    @Override
    public boolean capctchaValid(HttpServletRequest request, String captcha) {
        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captcha");

        return captcha.equalsIgnoreCase(sessionCaptcha);

    }

    @Override
    public boolean insert(Map<String, String[]> parameterMap) {
        //创建user对象，封装用户数据
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取一个激活码，添加到user中
        generateCode(user);

        //发送邮件
        StringBuilder builder = new StringBuilder()
                .append("<h1>恭喜您，注册成功，请点击下面链接进行激活</h1>")
                .append("<a href=\"http://localhost:8080/web_heima_travel_war_exploded/user?action=active")
                .append("&code=")
                .append(user.getCode())
                .append("\">http://localhost:8080/web_heima_travel_war_exploded/user?action=active")
                .append("&code=")
                .append(user.getCode())
                .append("</a>");
        try {
            MailUtil.sendEmail(user.getEmail(), user.getRealname(), builder.toString());
        } catch (EmailException e) {
            e.printStackTrace();
        }

        //数据插入数据库
        return dao.insert(user) == 1;

    }

    @Override
    public boolean active(String code) {
        return dao.active(code) == 1;
    }

    @Override
    public User queryByEmailAndPassword(String email, String password) {

        List<User> users = dao.queryByEmailAndPassword(email, password);
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public boolean activeValid(User user) {
        return user.getStatus() == 1;
    }

    /**
     * 生成校验码
     *
     * @param user 生成的校验码放入user对象中
     */
    public void generateCode(User user) {
        user.setCode(UUIDUtil.getUuid());
    }
}
