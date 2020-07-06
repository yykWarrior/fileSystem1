package com.rb.fs.entity;

/**
 * 分页封装实体
 */


public class PageBean<T> {
    //当前页
    private int page;
    //当前页记录条数
    private int limit;
    //查询字段
    private String processName;
    private String deviceName;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public PageBean(int page, int limit, String processName, String deviceName) {
        this.page = page;
        this.limit = limit;
        this.processName = processName;
        this.deviceName = deviceName;
    }
}
