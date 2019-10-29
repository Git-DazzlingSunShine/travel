package com.itheima.service;

import com.itheima.daomain.Category;

import java.util.List;

/**
 * 导航栏信息业务层
 */
public interface CategoryService {

    /**
     * 查询导航栏信息
     * @return
     */
    List<Category> queryAll();
}
