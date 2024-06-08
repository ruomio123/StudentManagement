package com.situ.student2024.util;

import com.github.pagehelper.PageInfo;

/**
 * 功能：
 *
 * @author 千堆雪
 * @version 1.0.0
 * @since 2024/1/10
 * <p>
 * created by 千堆雪 on 2024/1/10, last modified by 千堆雪 on 2024/1/10
 */
public class PaginateInfo {
    private final int pageNo;//页码
    private final int pageSize;//页面大小

    private PageInfo<?> pageInfo;

    public PaginateInfo(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public static PaginateInfo of(int pageNo, int pageSize) {
        return new PaginateInfo(pageNo, pageSize);
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    //获取查询时偏移位置
    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    //每次查询的结果集数量
    public int getLimit() {
        return pageSize;
    }


    public PageInfo<?> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<?> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
