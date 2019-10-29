package com.itheima.daomain;

import java.util.List;

/**
 * 旅游线路分页实体
 */
public class RoutePageBean {
    //旅游线路实体数据
    private List<Route> routes;

    //一共多少页
    private int pageCount;

    //一共多少条数据
    private int recordCount;

    //当前页数,默认值1
    private int currentPage=1;

    //查询时LIMIT 的第一个参数
    private int pageOffset;

    //LIMIT的第二个参数,默认一次8个数据
    private int pageSize=8;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
