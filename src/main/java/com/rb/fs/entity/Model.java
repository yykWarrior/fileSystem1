package com.rb.fs.entity;

import javax.persistence.*;

/**
 * 型号表
 */
@Table(name="model")
@Entity
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 型号名称
     */
    @Column(name = "md_name")
    private String name;
    /**
     * 轴承类型
     */
    @Column(name = "md_type")
    private String type;
    /**
     * 生产线id
     */
    private int proline_id;


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

    public int getProline_id() {
        return proline_id;
    }

    public void setProline_id(int proline_id) {
        this.proline_id = proline_id;
    }

    public Model() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Model(String name, String type, int proline_id) {
        this.name = name;
        this.type = type;
        this.proline_id = proline_id;
    }
}
