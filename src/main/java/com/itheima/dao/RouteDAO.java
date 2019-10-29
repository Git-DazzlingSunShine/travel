package com.itheima.dao;

import com.itheima.daomain.Route;

import java.util.List;

/**
 * 精选数据dao层
 */
public interface RouteDAO {
    /**
     * 人气旅游
     * @return 人气最高排行榜的前4个数据
     */
    List<Route> populate();

    /**
     *最新旅游
     * @return 发布时间最新的4条数据
     */
    List<Route> neweast();

    /**
     *主题路线
     * @return 4个主题路线（按照发布时间降序）
     */
    List<Route> theme();

    /**
     * 查询分页数据
     * @param word 查询关键字
     * @param cid 查询类别id
     * @param pageOffset 分页LIMIT的第一个参数
     * @param pageSize 分页LIMIT的第二个参数
     * @return 数据集合
     */
    List<Route> queryPage(String word, String cid, int pageOffset, int pageSize);


    /**
     * 查询数据共记录数
     * @param word 查询关键字
     * @param cid 查询的数据类别id
     * @return 数据总记录数
     */
    int queryRecordCount(String word, String cid);

    /**
     * 查询路线实体
     * @param rid 路线rid
     * @return 路线实体
     */
    Route queryByRId(String rid);
}
