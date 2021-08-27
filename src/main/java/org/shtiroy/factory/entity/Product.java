package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    public Product() {
    }

    public Product(String name, Boolean status) {
        this.name = name;
        this.status = status;
    }

    public Product(Integer id, String name, Boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product[" +
                "name= " + name +
                ", status=" + status +
                "]";
    }
}
