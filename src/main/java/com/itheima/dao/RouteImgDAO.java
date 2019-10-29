package com.itheima.dao;

import com.itheima.daomain.RouteImg;

import java.util.List;

public interface RouteImgDAO {
    /**
     * 查询路线图片
     * @param rid
     * @return 路线图片集合
     */
    List<RouteImg> queryByRid(String rid);
}
