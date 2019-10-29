package com.itheima.dao.impl;

import com.itheima.dao.SellerDAO;
import com.itheima.daomain.Seller;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SellerDAOImpl implements SellerDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(C3P0Util.getDataSource());

    @Override
    public Seller queryBySId(int sid) {
        String sql = "SELECT * FROM `tab_seller` WHERE sid=?";
        List<Seller> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        if (query.size() == 1) {
            return query.get(0);
        } else {
            return null;
        }
    }
}
