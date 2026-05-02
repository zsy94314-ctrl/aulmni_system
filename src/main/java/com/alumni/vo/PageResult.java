package com.alumni.vo;

import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
    private long total;
    private List<T> list;
    private long pageNum;
    private long pageSize;
    private long pages;

    public PageResult(long total, List<T> list, long pageNum, long pageSize) {
        this.total = total;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (total + pageSize - 1) / pageSize;
    }
}
