package com.rb.fs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 分配记录实体
 */
@Entity
@Table(name = "disrecord")
public class Distribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 生产线名称
     */
    @Column(name = "proline_id")
    private int prolineName;
    /**
     * 设备名称
     */
    @Column(name = "dev_id")
    private int dname;
    /**
     * 型号名称
     */
    @Column(name = "t_id")
    private int tname;
    /**
     * 文件id
     */
    @Column(name = "f_id")
    private String fid;
    /**
     * 创建时间
     */
    private Date creatdate;
    /**
     * 是否有效
     */
    @Column(name="effect")
    private int effect;
    /**
     * 开始时间
     */
    @Column(name = "startdate")
    private Date startdate;
    /**
     * 结束时间
     */
    @Column(name="enddate")
    private Date enddate;


    public void setProlineName(int prolineName) {
        this.prolineName = prolineName;
    }

    public void setDname(int dname) {
        this.dname = dname;
    }

    public int getProlineName() {
        return prolineName;
    }

    public int getDname() {
        return dname;
    }

    public int getTname() {
        return tname;
    }

    public void setTname(int tname) {
        this.tname = tname;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public Distribute() {
    }

    public Distribute(int prolineName, int dname, int tname, String fid, Date creatdate, int effect, Date startdate, Date enddate) {
        this.prolineName = prolineName;
        this.dname = dname;
        this.tname = tname;
        this.fid = fid;
        this.creatdate = creatdate;
        this.effect = effect;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public Distribute(int prolineName, int dname, int tname, String fid, int effect) {
        this.prolineName = prolineName;
        this.dname = dname;
        this.tname = tname;
        this.fid = fid;
        this.effect = effect;
    }
}
