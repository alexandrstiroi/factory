package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_component")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "id_product")
    private Product idProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    public Component() {
    }

    public Component(Product idProduct, String name, Integer type) {
        this.idProduct = idProduct;
        this.name = name;
        this.type = type;
    }

    public Component(Integer id, Product idProduct, String name, Integer type) {
        this.id = id;
        this.idProduct = idProduct;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Component[" +
                "idProduct= " + idProduct +
                ", name= " + name +
                "]";
    }
}
