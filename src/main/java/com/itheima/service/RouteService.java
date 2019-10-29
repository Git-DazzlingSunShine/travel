package com.itheima.service;

import com.itheima.daomain.Route;
import com.itheima.daomain.RoutePageBean;

import java.util.List;
import java.util.Map;

/**
 * 精选数据业务层
 */
public interface RouteService {

    /**
     * 查询精选数据，放入Map集合
     * @return
     */
    Map<String, Object> queryDistillation();

    /**
     *分页查询数据
     * @param word 查询关键字
     * @param cid 查询类别id
     * @param routePageBean 查询数据实体
     */
    void queryPage(String word, String cid, RoutePageBean routePageBean);


    /**
     * 查询收藏最多数据
     * @return 实体数据
     */
    List<Route> queryPopulate();

    /**
     * 查询路线详细信息
     * @param rid
     * @return 路线详情、商家、路线图片
     */
    Map<String, Object> queryDetail(String rid);
}
