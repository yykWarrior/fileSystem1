package com.rb.fs.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "role")
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 角色名称
     */
    @Column(name = "r_name")
    private String rname;
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

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
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

    public Role() {
    }

    public Role(String rname, Date createdate, String remark) {
        this.rname = rname;
        this.createdate = createdate;
        this.remark = remark;
    }
}