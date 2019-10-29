package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.daomain.RoutePageBean;
import com.itheima.service.FavoriteService;
import com.itheima.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YangLei
 * @version v1.0
 * @date 2019/10/8 22:52
 * @description TODO
 **/
@WebServlet(urlPatterns = "/favorite")
public class FavoriteServlet extends ActionServlet {
    private FavoriteService service = new FavoriteServiceImpl();

    protected void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        //创建路线JavaBean存放数据
        RoutePageBean routePageBean = new RoutePageBean();
        routePageBean.setPageSize(12);

        String currentPage = request.getParameter("currentPage");
        //设置当前页码
        if (currentPage != null && !currentPage.equals("")) {
            routePageBean.setCurrentPage(Integer.parseInt(currentPage));
        }

        //处理数据
        Object user = request.getSession().getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (user != null && !user.equals("")) {
            service.queryRouteByUid(request, routePageBean);
            //响应数据
            result.put("code", 0);
            result.put("msg", "operation success");
            result.put("routePageBean", routePageBean);
        } else {
            result.put("code", "-1");
            result.put("msg", "用户未登录");
        }

        String s = mapper.writeValueAsString(result);
        response.getWriter().write(s);
    }

    protected void favoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        RoutePageBean routePageBean = new RoutePageBean();

        String currentPage = request.getParameter("currentPage");
        if (currentPage != null && !currentPage.equals("")) {
            routePageBean.setCurrentPage(Integer.parseInt(currentPage));
        }

        String word = URLDecoder.decode(request.getParameter("word"), "utf-8");
        String priceBegin = request.getParameter("priceBegin");
        String priceEnd = request.getParameter("priceEnd");
        service.queryFavoriteRank(word, priceBegin, priceEnd, routePageBean);

        result.put("code", 0);
        result.put("msg", "operation success");
        result.put("routePageBean", routePageBean);
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);
    }
}
