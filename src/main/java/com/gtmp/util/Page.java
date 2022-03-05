package com.gtmp.util;

public class Page {
    private Integer size;        //一页多少条
    private Integer cur;         //当前第几页
    private Integer totalRecord; //一共多少条记录
    private Integer totalPage;   //一共多少页
    private String path;        // path

    /**
     * 获取参数，计算 totalPage
     */
    public void setProperties(Integer size, Integer cur, Integer totalRecord) {
        this.totalRecord = totalRecord;
        this.size = size;

        // 计算有几页
        if (totalRecord == 0)
            this.totalPage = 1;
        else if (totalRecord % size == 0)
            this.totalPage = totalRecord / size;
        else
            this.totalPage = totalRecord / size + 1;

        // 限制 page,防止非法参数
        if (cur == null || cur <= 0)
            this.cur = 1;
        else if (cur > this.totalPage)
            this.cur = totalPage;
        else
            this.cur = cur;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCur() {
        return cur;
    }

    public void setCur(Integer cur) {
        this.cur = cur;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}