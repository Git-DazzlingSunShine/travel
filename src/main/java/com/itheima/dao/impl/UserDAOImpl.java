package com.itheima.dao.impl;

import com.itheima.dao.UserDAO;
import com.itheima.daomain.User;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(C3P0Util.getDataSource());

    @Override
    public List<User> queryByEmail(String email) {
        String sql = "SELECT * FROM tab_user WHERE email=?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email);
    }

    @Override
    public int insert(User user) {
        String sql = "INSERT INTO tab_user(`username`, `password`, `realname`, `birthday`, `gender`, `mobile`, `email`, `code`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getRealname(),
                user.getBirthday(),
                user.getGender(),
                user.getMobile(),
                user.getEmail(),
                user.getCode());
    }

    @Override
    public int active(String code) {
        String sql = "UPDATE tab_user SET status=1 WHERE code=?";
        return jdbcTemplate.update(sql, code);
    }

    @Override
    public List<User> queryByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM tab_user WHERE email=? AND password=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email, password);
    }



}
