package com.rb.fs.dto;


public class ProLineDto {

    private int id;

    private String name;

    private String company;

    private String creatdate;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(String creatdate) {
        this.creatdate = creatdate;
    }

    public ProLineDto() {
    }

    public ProLineDto(int id, String name, String company, String creatdate) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.creatdate = creatdate;
    }
}
