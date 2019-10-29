package com.itheima.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.CategoryDAO;
import com.itheima.daomain.Category;
import com.itheima.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRedisDAO implements CategoryDAO {
    private Jedis jedis = JedisUtil.getJedis();

    @Override
    public List<Category> queryAll() {
        List<String> categories = jedis.lrange("heima.travel.categories", 0, -1);

        List<Category> result = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        //将json字符串数封装为Category对象
        for (String categoryString : categories) {
            Category category = null;
            try {
                category = mapper.readValue(categoryString, Category.class);
            } catch (IOException a) {
                a.printStackTrace();
            }
            result.add(category);
        }
        return result;
    }


    @Override
    public int append(List<Category> categories) {
        ObjectMapper mapper = new ObjectMapper();
        int count = 0;
        for (Category category : categories) {
            try {
                String categoryString = mapper.writeValueAsString(category);
                Long rpush = jedis.rpush("heima.travel.categories", categoryString);
                count += rpush;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 查询路线种类
     *Redis不做
     * @param cid
     * @return 路线种类实体
     */
    @Override
    public Category queryByCid(int cid) {
        return null;
    }

}
