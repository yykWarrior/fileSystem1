package com.rb.fs.entity;

import javax.persistence.*;
import java.util.Date;
@Table(name = "idea")
@Entity
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 内容
     */
    private String content;
    /**
     * 意见人
     */
    private String opponent;
    /**
     * 图片地址
     */
    private String imgpath;
    /**
     * 提交时间
     */
    private Date subtime;
    /**
     * 是否解决
     */
    private int resolve;
    /**
     * 说明描述
     */
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Date getSubtime() {
        return subtime;
    }

    public void setSubtime(Date subtime) {
        this.subtime = subtime;
    }

    public int getResolve() {
        return resolve;
    }

    public void setResolve(int resolve) {
        this.resolve = resolve;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Idea() {
    }

    public Idea(String content, String opponent, String imgpath, Date subtime, int resolve, String remark) {
        this.content = content;
        this.opponent = opponent;
        this.imgpath = imgpath;
        this.subtime = subtime;
        this.resolve = resolve;
        this.remark = remark;
    }
}
