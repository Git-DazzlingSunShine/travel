package com.itheima.dao.impl;

import com.itheima.dao.RouteImgDAO;
import com.itheima.daomain.RouteImg;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDAOImpl implements RouteImgDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(C3P0Util.getDataSource());

    @Override
    public List<RouteImg> queryByRid(String rid) {
        String sql = "SELECT * FROM `tab_route_img` WHERE rid=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
