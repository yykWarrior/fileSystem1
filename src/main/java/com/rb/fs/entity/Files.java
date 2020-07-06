package com.rb.fs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 文件表
 */
@Entity
@Table(name = "files")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
     * 上传时间
     */
    @Column(name = "updatetime")
    private Date update;
    /**
     * 是否有效
     */
    private int effect;
    /**
     * 文件路径
     */
    private String path;


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

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public Files() {
    }


    public Files(String name, String code, String version, Date update, int effect, String path) {
        this.name = name;
        this.code = code;
        this.version = version;
        this.update = update;
        this.effect = effect;
        this.path = path;
    }

}
