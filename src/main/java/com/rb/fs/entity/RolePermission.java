package com.rb.fs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色权限表
 */
@Entity
@Table(name = "role_permission")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 权限id
     */
    @Column(name = "p_id")
    private int pid;
    /**
     * 角色id
     */
    @Column(name = "r_id")
    private int rid;
    private Date createdate;

    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public RolePermission() {
    }

    public RolePermission(int pid, int rid, Date createdate, String remark) {
        this.pid = pid;
        this.rid = rid;
        this.createdate = createdate;
        this.remark = remark;
    }
}
