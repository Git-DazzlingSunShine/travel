package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.daomain.Route;
import com.itheima.daomain.RoutePageBean;
import com.itheima.daomain.User;
import com.itheima.service.FavoriteService;
import com.itheima.service.RouteService;
import com.itheima.service.impl.FavoriteServiceImpl;
import com.itheima.service.impl.RouteServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/route")
public class RouteServlet extends ActionServlet {
    private RouteService service = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    //查询精选数据
    protected void distillation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        //响应数据存入Map集合
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "operation success");

        // 把查询出来的精选数据，再放到result当中
        Map<String, Object> distillation = service.queryDistillation();
        result.put("distillation", distillation);

        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);
    }

    //查询分页路线
    protected void queryPageRoutes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "operation success");
        RoutePageBean routePageBean = new RoutePageBean();
        String currentPage = request.getParameter("currentPage");

        //若前端闯过来了当前页信息，就设置当前页，若没传使用默认
        if (currentPage != null && !currentPage.equals("")) {
            routePageBean.setCurrentPage(Integer.parseInt(currentPage));
        }

        //前端发送的查询类别参数cid
        String cid = request.getParameter("cid");

        //搜索的关键字
        String word = URLDecoder.decode(request.getParameter("word"), "utf-8");


        service.queryPage(word, cid, routePageBean);//进行查询业务处理
        result.put("routePageBean", routePageBean);

        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);
    }

    //查询热门路线
    protected void queryPopulate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        List<Route> populate = service.queryPopulate();

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "operation success");
        result.put("populate", populate);
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(result);

        response.getWriter().write(resultJson);
    }

    //查询路线详情
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        String rid = request.getParameter("rid");
        Map<String, Object> routeDetail = service.queryDetail(rid);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("mag", "operation success");
        result.put("detail", routeDetail);

        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);
    }

    //查询收藏状态
    protected void queryFavoriteStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        String rid = request.getParameter("rid");

        //查询是否收藏过
        User user = (User) request.getSession().getAttribute("user");
        boolean favored = favoriteService.isFavored(rid, user.getUid());

        //响应数据
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "operation success");
        result.put("isFavored", favored);

        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);
    }

    //收藏路线
    protected void favorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        // 1. 接收数据
        String rid = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");
        favoriteService.favor(rid, user.getUid());

        result.put("code", 0);
        result.put("msg", "operation success");
        String resultJson = mapper.writeValueAsString(result);
        response.getWriter().write(resultJson);


    }
}