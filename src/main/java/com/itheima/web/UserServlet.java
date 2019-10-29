package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.daomain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends ActionServlet {
    private UserService service = new UserServiceImpl();

    protected void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //激活验证
        response.setContentType("text/html;charset=utf-8");
        String code = request.getParameter("code");
        boolean active = service.active(code);
        System.out.println("active = " + active);

        response.getWriter().write("<h1>恭喜您，账号已激活成功！3秒后跳转到登录页面</h1>");
        //3秒后跳转
        response.setHeader("Refresh", "3; url=http://localhost:8080/web_heima_travel_war_exploded/login.html");

    }

    //邮箱注册，唯一性验证
    protected void checkEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");

        String email = request.getParameter("email");
        //将邮箱查询的结果发送到浏览器
        if (service.existEmail(email)) {
            response.getWriter().write("false");
        } else {
            response.getWriter().write("true");
        }
    }

    //注册验证
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        //获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        String email = request.getParameter("email");
        String captcha = request.getParameter("captcha");

        //创建Map存储响应数据
        Map<String, Object> result = new HashMap<>();
        if (service.existEmail(email)) {
            result.put("code", 1);
            result.put("msg", "邮箱已存在，请确认后重试");
        } else if (!service.capctchaValid(request, captcha)) {
            //验证码校验
            result.put("code", 2);
            result.put("msg", "验证码错误");
        } else {
            service.insert(parameterMap);
            result.put("code", 0);
            result.put("msg", "operation success");
        }
        //数据处理完删除session中注册时的验证码
        request.getSession().removeAttribute("captcha");

        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);
    }

    //登录校验
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");

        //根据邮箱和密码查询用户
        User user = service.queryByEmailAndPassword(email, password);

        if (!service.capctchaValid(request, captcha)) {
            result.put("code", 1);
            result.put("msg", "验证码错误");
        } else if (user == null) {
            result.put("code", 2);
            result.put("msg", "Incorrect username or password");
        } else if (!service.activeValid(user)) {
            result.put("code", 3);
            result.put("msg", "Account is not activated");
        } else {
            result.put("code", 0);
            result.put("msg", "operation success");
            request.getSession().setAttribute("user", user);
        }

        //删除session中的验证码
        request.getSession().removeAttribute("capctcha");

        ObjectMapper mapper = new ObjectMapper();

        String resultJson = mapper.writeValueAsString(result);
        //响应数据
        response.getWriter().write(resultJson);
    }

    //查询当前用户
    protected void queryCurrentUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> result = new HashMap<>();
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            // 代表未登录
            result.put("code", 1);
            result.put("msg", "用户未登录");
        } else {
            // 代表已登录
            result.put("code", 0);
            result.put("msg", "operation success");
            result.put("user", user);
        }

        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);
    }

    //用户退出登录
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //删除session中用户数据
        request.getSession().removeAttribute("user");

        // 同步请求
        // 跳转到登录页面
        response.sendRedirect("login.html");

    }
}
