package com.rb.fs.dto;

public class NameAnIdDto {
    //生产线id
    private int lineid;
    //设备id
    private int did;
    //型号id
    private int mid;
    /**
     * 生产线名称
     */
    private String prolinename;
    /**
     * 设备名称
     */

    private String dname;
    /**
     * 型号名称
     */

    private String tname;

    public NameAnIdDto() {
    }


    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
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

    public NameAnIdDto(int lineid, int did, int mid, String prolinename, String dname, String tname) {
        this.lineid = lineid;
        this.did = did;
        this.mid = mid;
        this.prolinename = prolinename;
        this.dname = dname;
        this.tname = tname;
    }

    public int getLineid() {
        return lineid;
    }

    public void setLineid(int lineid) {
        this.lineid = lineid;
    }

    public String getProlinename() {
        return prolinename;
    }

    public void setProlinename(String prolinename) {
        this.prolinename = prolinename;
    }
}
