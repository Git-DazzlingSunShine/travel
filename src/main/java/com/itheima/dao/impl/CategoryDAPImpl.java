package com.itheima.dao.impl;

import com.itheima.dao.CategoryDAO;
import com.itheima.daomain.Category;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDAPImpl implements CategoryDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(C3P0Util.getDataSource());

    /**
     * 查询所有导航栏信息
     *
     * @return
     */
    @Override
    public List<Category> queryAll() {
        String sql = "SELECT * FROM tab_category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    /**
     * 插入数据
     *
     * @param categories 插入的数据
     * @return 受影响的记录有几条
     */
    @Override
    public int append(List<Category> categories) {
        // TODO: 向数据库当插入数据
        return 0;
    }

    /**
     * 查询路线种类
     *
     * @param cid
     * @return 路线种类实体
     */
    @Override
    public Category queryByCid(int cid) {
        String sql = "SELECT * FROM `tab_category` WHERE cid=?";
        List<Category> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), cid);
        if (query.size() == 1) {
            return query.get(0);
        }
        return null;
    }
}
