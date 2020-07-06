package com.rb.fs.dto;

/**
 * 操作员页面请求所有型号的dto
 */
public class PreModelDto {
    //生产线id
    private int lineId;
    //设备id
    private int did;
    //型号id
    private int id;
    //型号名称
    private String name;
    //型号类型
    private String type;
    private String lineName;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public PreModelDto(int lineId, int id, String name, String type, String lineName) {
        this.lineId = lineId;
        this.id = id;
        this.name = name;
        this.type = type;
        this.lineName = lineName;
    }

    public PreModelDto() {
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PreModelDto(int lineId, int did, int id, String name, String type) {
        this.lineId = lineId;
        this.did = did;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public PreModelDto(int lineId, int id, String name, String type) {
        this.lineId = lineId;
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
