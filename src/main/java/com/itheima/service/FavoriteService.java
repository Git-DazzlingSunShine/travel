package com.itheima.service;

import com.itheima.daomain.RoutePageBean;

import javax.servlet.http.HttpServletRequest;

public interface FavoriteService {
    /**
     * 收藏用户路线
     * @param rid
     * @param uid
     * @return
     */
    boolean favor(String rid, int uid);

    /**
     * 查询用户是否已收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavored(String rid, int uid);

    /**
     * 查询用户收藏
     * @param request
     * @param routePageBean
     */
    void queryRouteByUid(HttpServletRequest request, RoutePageBean routePageBean);

    /**
     * 查询路线收藏排行榜
     * @param word 搜索关键字
     * @param priceBegin 最低价
     * @param priceEnd 最高价
     * @param routePageBean 路线模型JavaBean
     */
    void queryFavoriteRank(String word, String priceBegin, String priceEnd, RoutePageBean routePageBean);
}
