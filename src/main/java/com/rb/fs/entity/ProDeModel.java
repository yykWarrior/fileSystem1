package com.rb.fs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 上传文件整合生产线，设备，型号，文件
 */
@Table(name = "filetype")
@Entity
public class ProDeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    /**
     * 生产线名称
     */
    @Column(name = "p_id")
    private int proName;
    /**
     * 设备名称
     */
    @Column(name = "d_id")
    private int deviceName;
    /**
     * 型号名称
     */
    @Column(name = "t_id")
    private int modelName;
    /**
     * 文件名称
     */
    @Column(name = "f_id")
    private int fName;
    /**
     * 是否有效
     */
    private int effect;
    /**
     * 文件存放路径
     */
    private String path;
    /**
     * 上传时间
     */
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public ProDeModel(int proName, int deviceName, int modelName, int fName, int effect, String path, Date updatetime) {
        this.proName = proName;
        this.deviceName = deviceName;
        this.modelName = modelName;
        this.fName = fName;
        this.effect = effect;
        this.path = path;
        this.updatetime = updatetime;
    }

    public int getProName() {
        return proName;
    }

    public void setProName(int proName) {
        this.proName = proName;
    }

    public int getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(int deviceName) {
        this.deviceName = deviceName;
    }

    public int getModelName() {
        return modelName;
    }

    public void setModelName(int modelName) {
        this.modelName = modelName;
    }

    public int getfName() {
        return fName;
    }

    public void setfName(int fName) {
        this.fName = fName;
    }

    public ProDeModel(int proName, int deviceName, int modelName, int fName, int effect, Date updatetime) {
        this.proName = proName;
        this.deviceName = deviceName;
        this.modelName = modelName;
        this.fName = fName;
        this.effect = effect;
        this.updatetime = updatetime;
    }

    public ProDeModel() {
    }
}
