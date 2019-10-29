package com.itheima.dao;

import com.itheima.daomain.Favorite;
import com.itheima.daomain.Route;

import java.util.List;

public interface FavoriteDAO {
    /**
     * 添加收藏
     * @return
     * @param rid
     * @param uid
     */
    int addFavor(String rid, int uid);

    /**
     * 查询收藏
     * @return
     * @param rid
     * @param uid
     */
    Favorite queryByRidAndUid(String rid, int uid);

    /**
     * 查询用户收藏路线
     * @return
     * @param userUid 用户id
     * @param pageOffset LIMIT 第一个参数
     * @param pageSize LIMIT 第二个参数
     */
    List<Route> queryRouteByUid(int userUid, int pageOffset, int pageSize);

    /**
     * 查询用户收藏路线总数
     * @return
     * @param uid
     */
    int queryByUidCount(int uid);

    /**
     * 按条件查询用户数据总条数
     * @param word 查询关键字
     * @param priceBegin 最低价
     * @param priceEnd 最高价
     * @return 数据总数
     */
    int queryRecordCount(String word, String priceBegin, String priceEnd);

    /**
     *
     * @return
     * @param s
     * @param priceBegin
     * @param word
     * @param pageOffset LIMIT 第一个参数
     * @param pageSize LIMIT 第二个参数
     */
    List<Route> queryRoutes(String word, String priceBegin, String priceEnd, int pageOffset, int pageSize);
}
