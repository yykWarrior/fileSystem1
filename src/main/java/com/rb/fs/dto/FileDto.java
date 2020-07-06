package com.rb.fs.dto;

/**
 * 文件封装类
 */
public class FileDto {

    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 版本
     */
    private String version;
    /**
     * 生产线id
     */
    private String  proline_id;
    /**
     * 设备id
     */
    private String device_id;
    /**
     * 型号id
     */
    private String model_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProline_id() {
        return proline_id;
    }

    public void setProline_id(String proline_id) {
        this.proline_id = proline_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public FileDto() {
    }

    public FileDto(String name, String code, String version, String proline_id, String device_id, String model_id) {
        this.name = name;
        this.code = code;
        this.version = version;
        this.proline_id = proline_id;
        this.device_id = device_id;
        this.model_id = model_id;
    }
}
