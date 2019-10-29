package com.itheima.dao.impl;

import com.itheima.dao.RouteDAO;
import com.itheima.daomain.Route;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDAOImpl implements RouteDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(C3P0Util.getDataSource());

    @Override
    public List<Route> populate() {
        String sql = "SELECT * FROM `tab_route` WHERE rflag=1 ORDER BY count DESC LIMIT 0,4";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }


    @Override
    public List<Route> neweast() {
        String sql = "SELECT * FROM `tab_route` WHERE rflag=1 ORDER BY rdate DESC LIMIT 0,4";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }


    @Override
    public List<Route> theme() {
        String sql = "SELECT * FROM `tab_route` WHERE rflag=1 AND isThemeTour=1 ORDER BY rdate DESC LIMIT 0,4";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> queryPage(String word, String cid, int pageOffset, int pageSize) {

        //存放动态SQL语句
        StringBuilder builder = new StringBuilder();
        //SQL语句的参数
        List<Object> parameters = new ArrayList<>();

        builder.append("SELECT * FROM tab_route WHERE rflag=1");

        //1.若有关键字word，就加入SQL中
        if (word != null && !word.equals("") && !word.equals("null")) {
            builder.append(" AND rname LIKE ?");
            parameters.add("%" + word + "%");
        }

        //2.查询类别参数
        if (cid != null && !cid.equals("") && !word.equals("null")) {
            builder.append(" AND cid=?");
            parameters.add(cid);
        }

        //最后的分页数据
        builder.append(" LIMIT ?,?");
        parameters.add(pageOffset);
        parameters.add(pageSize);
        return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(Route.class), parameters.toArray());
    }

    @Override
    public int queryRecordCount(String word, String cid) {
        //动态SQL
        StringBuilder builder = new StringBuilder();
        //SQL参数
        List<Object> parameters = new ArrayList<>();

        builder.append("SELECT COUNT(*) FROM tab_route WHERE rflag=1");

        //1.若有关键字word，就加入SQL中
        if (word != null && !word.equals("") && !word.equals("null")) {
            builder.append(" AND rname LIKE ?");
            parameters.add("%" + word + "%");
        }

        //2.查询类别参数
        if (cid != null && !cid.equals("")&& !word.equals("null")) {
            builder.append(" AND cid=?");
            parameters.add(cid);
        }

        return jdbcTemplate.queryForObject(builder.toString(), parameters.toArray(), Integer.class);
    }

    /**
     * 查询路线实体
     *
     * @param rid 路线rid
     * @return 路线实体
     */
    @Override
    public Route queryByRId(String rid) {
        String sql = "SELECT * FROM `tab_route` WHERE rflag=1 AND rid=?";
        List<Route> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        if (query.size() == 1) {
            return query.get(0);
        }
        return null;
    }
}
