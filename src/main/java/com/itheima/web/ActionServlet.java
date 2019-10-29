package com.itheima.web;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class ActionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        //利用反射机制，使用父类class对象泛型，使用方法名得到对应的子类方法
        Class cls = this.getClass();

        try {
            Method actionMethond = cls.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            actionMethond.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}
