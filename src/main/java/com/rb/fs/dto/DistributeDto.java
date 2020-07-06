package com.rb.fs.dto;

import java.util.Date;

/**
 * 分配记录dto
 */
public class DistributeDto {
    private int id;
    /**
     * 生产线名称
     */
    private String prolineName;
    /**
     * 设备名称
     */

    private String dname;
    /**
     * 型号名称
     */

    private String tname;
    /**
     * 文件id
     */

    private String fid;
    /**
     * 创建时间
     */
    private String creatdate;
    /**
     * 是否有效
     */

    private int effect;
    /**
     * 开始时间
     */

    private String startdate;
    /**
     * 结束时间
     */

    private String enddate;
    /**
     * 文件路径
     */
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProlineName() {
        return prolineName;
    }

    public void setProlineName(String prolineName) {
        this.prolineName = prolineName;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

   /* public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }*/

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

   /* public Date getStartdate() {
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
    }*/

    public String getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(String creatdate) {
        this.creatdate = creatdate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public DistributeDto(int id, String prolineName, String dname, String tname, String fid, String creatdate, int effect, String startdate, String enddate) {
        this.id = id;
        this.prolineName = prolineName;
        this.dname = dname;
        this.tname = tname;
        this.fid = fid;
        this.creatdate = creatdate;
        this.effect = effect;
        this.startdate = startdate;
        this.enddate = enddate;
    }
    /* public DistributeDto(int id, String prolineName, String dname, String tname, String fid, Date creatdate, int effect, Date startdate, Date enddate) {
        this.id = id;
        this.prolineName = prolineName;
        this.dname = dname;
        this.tname = tname;
        this.fid = fid;
        this.creatdate = creatdate;
        this.effect = effect;
        this.startdate = startdate;
        this.enddate = enddate;
    }*/

    public DistributeDto() {
    }

    public DistributeDto(String prolineName, String dname, String tname) {
        this.prolineName = prolineName;
        this.dname = dname;
        this.tname = tname;
    }

    public DistributeDto(int id, String prolineName, String dname, String tname) {
        this.id = id;
        this.prolineName = prolineName;
        this.dname = dname;
        this.tname = tname;
    }

    public DistributeDto(int id, String prolineName, String dname, String tname, String fid, String url) {
        this.id = id;
        this.prolineName = prolineName;
        this.dname = dname;
        this.tname = tname;
        this.fid = fid;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
