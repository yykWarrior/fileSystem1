package com.rb.fs.entity;



import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 生产线表
 */
@Entity
@Table(name="product_line")
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="company")
    private String company;
    @Column(name="creatdate")
    private Date creatdate;

   /* @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="proline_id",referencedColumnName = "id")
    private Set<Device> devices;*/

    public ProductLine() {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }


    public ProductLine( String name,  Date creatdate) {
        this.name = name;
        this.creatdate = creatdate;
    }
}
