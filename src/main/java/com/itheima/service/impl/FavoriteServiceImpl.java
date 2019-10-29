package com.itheima.service.impl;

import com.itheima.dao.FavoriteDAO;
import com.itheima.dao.impl.FavoriteDAOImpl;
import com.itheima.daomain.Favorite;
import com.itheima.daomain.Route;
import com.itheima.daomain.RoutePageBean;
import com.itheima.daomain.User;
import com.itheima.service.FavoriteService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDAO dao = new FavoriteDAOImpl();

    @Override
    public boolean favor(String rid, int uid) {
        int result = dao.addFavor(rid, uid);
        return result == 2;
    }

    @Override
    public boolean isFavored(String rid, int uid) {
        Favorite favorite = dao.queryByRidAndUid(rid, uid);
        return favorite != null;
    }

    @Override
    public void queryRouteByUid(HttpServletRequest request, RoutePageBean routePageBean) {
        User user = (User) request.getSession().getAttribute("user");

        //1、查询用户收藏路线总条数
        int recordCount = dao.queryByUidCount(user.getUid());
        int pageCount = (int) Math.ceil(recordCount / (double) routePageBean.getPageSize());
        int pageOffset = (routePageBean.getCurrentPage() - 1) * routePageBean.getPageSize();

        routePageBean.setRecordCount(recordCount);
        routePageBean.setPageCount(pageCount);
        routePageBean.setPageOffset(pageOffset);

        List<Route> routes = dao.queryRouteByUid(user.getUid(), pageOffset, routePageBean.getPageSize());
        routePageBean.setRoutes(routes);
    }

    @Override
    public void queryFavoriteRank(String word, String priceBegin, String priceEnd, RoutePageBean routePageBean) {

        //查询路线总数
        int recordCount = dao.queryRecordCount(word, priceBegin, priceEnd);
        int pageCount = (int) Math.ceil(recordCount / (double) routePageBean.getPageSize());
        int pageOffset = (routePageBean.getCurrentPage() - 1) * routePageBean.getPageSize();
        List<Route> routes = dao.queryRoutes(word,priceBegin,priceEnd,pageOffset, routePageBean.getPageSize());

        routePageBean.setRecordCount(recordCount);
        routePageBean.setPageCount(pageCount);
        routePageBean.setPageOffset(pageOffset);
        routePageBean.setRoutes(routes);
    }
}
