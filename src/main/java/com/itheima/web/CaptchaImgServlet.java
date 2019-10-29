package com.itheima.web;

import com.itheima.utils.CaptchaUtil;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码
 */
@WebServlet("/captcha_img")
public class CaptchaImgServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 服务器通知浏览器不要缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");

        // 生成4位的随机验证码；保存至用户session中；生成验证码图片，并写到响应体中
        String captchaString = CaptchaUtil.generateString(4);
        request.getSession().setAttribute("captcha", captchaString);
        CaptchaUtil.generateImgToStream(captchaString, response.getOutputStream());

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
