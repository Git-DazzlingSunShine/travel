package com.itheima.dao;

import com.itheima.daomain.Category;

import java.util.List;

/**
 * 查询导航栏dao层
 */
public interface CategoryDAO {

    /**
     * 查询所有导航栏信息
     * @return
     */
    List<Category> queryAll();

    /**
     * 插入数据
     * @param categories 插入的数据
     * @return 受影响的记录有几条
     */
    int append(List<Category> categories);

    /**
     * 查询路线种类
     * @param cid
     * @return 路线种类实体
     */
    Category queryByCid(int cid);
}
