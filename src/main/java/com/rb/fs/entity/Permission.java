package com.rb.fs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限
 */
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "p_name")
    private String pname;

    private Date createdate;

    private String remark;

    public Permission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public Permission(String pname, Date createdate, String remark) {
        this.pname = pname;
        this.createdate = createdate;
        this.remark = remark;
    }
}
