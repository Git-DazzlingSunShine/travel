package com.itheima.dao;

import com.itheima.daomain.Seller;

public interface SellerDAO {
    /**
     * 查询商家实体
     * @param sid 商家id
     * @return 商家实体
     */
    Seller queryBySId(int sid);
}
