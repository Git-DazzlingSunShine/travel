package com.itheima.service.impl;

import com.itheima.dao.CategoryDAO;
import com.itheima.dao.impl.CategoryDAPImpl;
import com.itheima.dao.impl.CategoryRedisDAO;
import com.itheima.daomain.Category;
import com.itheima.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO dbDAO = new CategoryDAPImpl();
    private CategoryDAO redisDAO = new CategoryRedisDAO();

    @Override
    public List<Category> queryAll() {
        List<Category> result = redisDAO.queryAll();
        if (result.size() == 0) {
            //代表redis里面没数据
            result = dbDAO.queryAll();
            redisDAO.append(result);
        }

        return result;
    }
}
