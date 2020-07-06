package com.rb.fs.entity;

import javax.persistence.*;

/**
 * 团队成员实体
 */
@Entity
@Table(name = "temember")
public class Temember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 成员名称
     */
    private String name;
    /**
     * 号位
     */
    private String postion;
    /**
     * 所属团队
     */
    @Column(name = "teamid")
    private int teamId;
    /**
     * 是否团队长
     */
    private int leader;
    /**
     * 说明
     */
    private String status;

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

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Temember(String name, String postion, int teamId, int leader, String status) {
        this.name = name;
        this.postion = postion;
        this.teamId = teamId;
        this.leader = leader;
        this.status = status;
    }

    public Temember(String name, String postion, int teamId, int leader) {
        this.name = name;
        this.postion = postion;
        this.teamId = teamId;
        this.leader = leader;
    }
}
