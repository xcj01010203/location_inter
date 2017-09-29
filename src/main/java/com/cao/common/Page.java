package com.cao.common;

import java.util.List;

public class Page {
    public static final int DEFAULT_PAGESIZE = 20;
    private int pageSize = DEFAULT_PAGESIZE;
    private Integer currentPage = 1;
    private Integer totalRows;
    private int totalPage;
    private List<?> result;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        if (this.currentPage > this.totalPage) {
            this.setCurrentPage(totalPage);
        } else if (this.currentPage < 1) {
            this.setCurrentPage(1);
        }
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        if (totalRows > 0) {
            if (totalRows % this.pageSize == 0) {
                this.totalPage = totalRows / this.pageSize;
            } else {
                this.totalPage = totalRows / this.pageSize + 1;
            }
        }
        this.totalRows = totalRows;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

    public int getOffset() {
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }
        if (this.pageSize <= 0) {
            this.pageSize = DEFAULT_PAGESIZE;
        }
        return (currentPage - 1) * pageSize;
    }
}
