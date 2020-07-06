package com.rb.fs.dto;

/**
 * 生产线设备封装类
 */
public class DeLineDto {

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
    private int proline_id;

    private String name;

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

    public int getProline_id() {
        return proline_id;
    }

    public void setProline_id(int proline_id) {
        this.proline_id = proline_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeLineDto() {
    }

    public DeLineDto(int id, String process_name, String d_type, int proline_id, String name) {

        this.id = id;
        this.process_name = process_name;
        this.d_type = d_type;
        this.proline_id = proline_id;
        this.name = name;
    }

    public DeLineDto(String process_name, String d_type, int proline_id, String name) {
        this.process_name = process_name;
        this.d_type = d_type;
        this.proline_id = proline_id;
        this.name = name;
    }
}
