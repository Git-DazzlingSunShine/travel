package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.daomain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/category")
public class CategoryServlet extends ActionServlet {
    CategoryService service = new CategoryServiceImpl();

    /*
     * 查询类别
     */
    protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        Map<String, Object> result = new HashMap<>();
        List<Category> categories = service.queryAll();

        result.put("code", 0);
        result.put("msg", "operation success");
        result.put("categories", categories);

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(result);
        response.getWriter().write(s);
    }
}
