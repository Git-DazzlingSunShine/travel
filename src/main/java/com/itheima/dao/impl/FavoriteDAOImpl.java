package com.itheima.dao.impl;

import com.itheima.dao.FavoriteDAO;
import com.itheima.daomain.Favorite;
import com.itheima.daomain.Route;
import com.itheima.utils.C3P0Util;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAOImpl implements FavoriteDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(C3P0Util.getDataSource());

    @Override
    public int addFavor(String rid, int uid) {
        //初始化事务
//        TransactionSynchronizationManager.initSynchronization();

        int count = 0;
        Connection connection = null;
        try {
            connection = C3P0Util.getConnection();
            connection.setAutoCommit(false);
            //1.tab_favorite表中插入用户收藏路线
            String sql = "INSERT INTO `tab_favorite` VALUE(?,now(),?)";

            PreparedStatement statm = connection.prepareStatement(sql);
            statm.setInt(1, Integer.parseInt(rid));
            statm.setInt(2, uid);
            int update1 = statm.executeUpdate();

//            int update1 = jdbcTemplate.update(sql, rid, uid);
            count += update1;

            //2.tab_route表中收藏总数+1
            sql = "UPDATE `tab_route` SET count=count+1 WHERE rid=?";
//            int update2 = jdbcTemplate.update(sql, rid);
            PreparedStatement statm2 = connection.prepareStatement(sql);
            statm2.setInt(1, Integer.parseInt(rid));
            int update2 = statm2.executeUpdate();
            count += update2;
            connection.commit();
            statm.close();
            statm2.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public Favorite queryByRidAndUid(String rid, int uid) {
        String sql = "SELECT * FROM `tab_favorite` WHERE rid=? AND uid=?";
        List<Favorite> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        if (query.size() == 1) {
            return query.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Route> queryRouteByUid(int userUid, int pageOffset, int pageSize) {
        String sql = "SELECT tab_route.* FROM tab_route INNER JOIN tab_favorite WHERE tab_route.rid=tab_favorite.rid AND tab_route.rflag=1 AND tab_favorite.uid=? ORDER BY count DESC LIMIT ?,?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), userUid, pageOffset, pageSize);
    }

    @Override
    public int queryByUidCount(int uid) {
        String sql = "SELECT COUNT(*) FROM `tab_favorite` WHERE uid=?";

        return jdbcTemplate.queryForObject(sql, Integer.class, uid);
    }

    @Override
    public int queryRecordCount(String word, String priceBegin, String priceEnd) {
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<>();

        builder.append("SELECT COUNT(*) FROM `tab_route` WHERE rflag=1");
        if (word != null && !word.equals("") && !word.equals("null")) {
            builder.append(" AND rname LIKE ?");
            params.add("%" + word + "%");
        }

        if (priceBegin != null && !priceBegin.equals("")) {
            builder.append(" AND price>=?");
            params.add(priceBegin);
        }

        if (priceEnd != null && !priceEnd.equals("")) {
            builder.append(" AND price<=?");
            params.add(priceEnd);
        }


        return jdbcTemplate.queryForObject(builder.toString(), Integer.class, params.toArray());

    }

    @Override
    public List<Route> queryRoutes(String word, String priceBegin, String priceEnd, int pageOffset, int pageSize) {
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        builder.append("SELECT * FROM `tab_route` WHERE rflag=1");

        if (word != null && !word.equals("") && !word.equals("null")) {
            builder.append(" AND rname LIKE ?");
            params.add("%" + word + "%");
        }

        if (priceBegin != null && !priceBegin.equals("")) {
            builder.append(" AND price>=?");
            params.add(priceBegin);
        }

        if (priceEnd != null && !priceEnd.equals("")) {
            builder.append(" AND price<=?");
            params.add(priceEnd);
        }

        builder.append(" ORDER BY count DESC LIMIT ?, ?");
        params.add(pageOffset);
        params.add(pageSize);
        return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }
}
