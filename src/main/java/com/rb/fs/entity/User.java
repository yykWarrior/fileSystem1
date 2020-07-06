package com.rb.fs.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 */
@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 生产线id
     */
    private String proline;
    /**
     * 描述
     */
    private String remark;


    public User(int id, String name, String password, Date createdate, String proline, String remark) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.createdate = createdate;
        this.proline = proline;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProline() {
        return proline;
    }

    public void setProline(String proline) {
        this.proline = proline;
    }

    public User(String name, String password, Date createdate, String proline, String remark) {
        this.name = name;
        this.password = password;
        this.createdate = createdate;
        this.proline = proline;
        this.remark = remark;
    }
}