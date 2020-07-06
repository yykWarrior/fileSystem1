package com.rb.fs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色用户表
 */
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 用户id
     */
    @Column(name = "u_id")
    private int uid;
    /**
     * 角色
     */
    @Column(name = "r_id")
    private int rid;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 描述
     */
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserRole() {
    }

    public UserRole(int uid, int rid, Date createdate, String remark) {
        this.uid = uid;
        this.rid = rid;
        this.createdate = createdate;
        this.remark = remark;
    }
}
