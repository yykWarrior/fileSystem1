package com.rb.fs.entity;

import java.util.List;

public class ReturnBean {
    private List objectList;
    private int count;



    public List getObjectList() {
        return objectList;
    }

    public void setObjectList(List objectList) {
        this.objectList = objectList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ReturnBean() {
    }

    public ReturnBean(List objectList, int count) {
        this.objectList = objectList;
        this.count = count;
    }
}
