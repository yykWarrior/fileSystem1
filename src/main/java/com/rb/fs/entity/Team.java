package com.rb.fs.entity;

import javax.persistence.*;

/**
 * 团队实体
 */
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 团队名称
     */
    private String name;
    /**
     * 团队成员数量
     */
    private int numbers;
    /**
     * 描述
     */
    private String status;

    public Team(String name) {
        this.name = name;
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

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Team(int id, String name, int numbers, String status) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
        this.status = status;
    }

    public Team(String name, int numbers, String status) {
        this.name = name;
        this.numbers = numbers;
        this.status = status;
    }
}
