package com.rb.fs.entity;

import javax.persistence.*;

/**
 * 设备表
 */
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 工序名称
     */
    private String process_name;
    /**
     * 设备名称
     */
    private String d_type;
    /**
     * 生产线id
     */
    @Column(name="proline_id")
    private int proline_id;

    public Device(String process_name, String d_type, int proline_id) {
        this.process_name = process_name;
        this.d_type = d_type;
        this.proline_id = proline_id;
    }

    public Device(String process_name, String d_type) {
        this.process_name = process_name;
        this.d_type = d_type;
    }

    public int getProline_id() {
        return proline_id;
    }

    public void setProline_id(int proline_id) {
        this.proline_id = proline_id;
    }

    public Device() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public String getD_type() {
        return d_type;
    }

    public void setD_type(String d_type) {
        this.d_type = d_type;
    }


}
