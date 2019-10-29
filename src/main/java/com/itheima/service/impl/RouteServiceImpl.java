package com.itheima.service.impl;

import com.itheima.dao.CategoryDAO;
import com.itheima.dao.RouteDAO;
import com.itheima.dao.RouteImgDAO;
import com.itheima.dao.SellerDAO;
import com.itheima.dao.impl.CategoryDAPImpl;
import com.itheima.dao.impl.RouteDAOImpl;
import com.itheima.dao.impl.RouteImgDAOImpl;
import com.itheima.dao.impl.SellerDAOImpl;
import com.itheima.daomain.*;
import com.itheima.service.RouteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements RouteService {
    private RouteDAO dao = new RouteDAOImpl();
    private SellerDAO sellerDAO = new SellerDAOImpl();
    private RouteImgDAO routeImgDAO = new RouteImgDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAPImpl();

    @Override
    public Map<String, Object> queryDistillation() {
        Map<String, Object> result = new HashMap<>();

        List<Route> populate = dao.populate();
        List<Route> neweast = dao.neweast();
        List<Route> theme = dao.theme();

        result.put("populate", populate);
        result.put("neweast", neweast);
        result.put("theme", theme);
        return result;
    }

    @Override
    public void queryPage(String word, String cid, RoutePageBean routePageBean) {
        int pageOffset = (routePageBean.getCurrentPage() - 1) * routePageBean.getPageSize();


        //获取查询实体数据
        int pageSize = routePageBean.getPageSize();
        List<Route> routes = dao.queryPage(word, cid, pageOffset, routePageBean.getPageSize());

        //获取该类别数据总条数
        int recordCount = dao.queryRecordCount(word, cid);

        //计算数据一共多少页
        int pageCount = (int) Math.ceil(recordCount / (double) routePageBean.getPageSize());

        //将查询和计算出来的数据填入routePageBean对象里面
        routePageBean.setPageOffset(pageOffset);
        routePageBean.setRecordCount(recordCount);
        routePageBean.setPageCount(pageCount);
        routePageBean.setRoutes(routes);

    }

    @Override
    public List<Route> queryPopulate() {
        return dao.populate();
    }

    @Override
    public Map<String, Object> queryDetail(String rid) {
        Map<String, Object> result = new HashMap<>();

        Route route = dao.queryByRId(rid);
        Seller seller = sellerDAO.queryBySId(route.getSid());
        List<RouteImg> routeImg = routeImgDAO.queryByRid(rid);
        Category category = categoryDAO.queryByCid(route.getCid());

        result.put("route", route);
        result.put("seller", seller);
        result.put("routeImg", routeImg);
        result.put("category", category);

        return result;

    }
}
