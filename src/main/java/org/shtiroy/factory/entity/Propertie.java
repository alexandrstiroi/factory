package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_propertie")
public class Propertie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Component.class)
    @JoinColumn(name = "id_component")
    private Component idComponent;

    @Column(name = "name")
    private String name;

    @Column(name = "lenght")
    private Double lenght;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "count")
    private Integer count;

    @Column(name = "type")
    private Integer type;

    public Propertie() {
    }

    public Propertie(Component idComponent, String name, Double lenght, Double width, Double height, Integer count, Integer type) {
        this.idComponent = idComponent;
        this.name = name;
        this.lenght = lenght;
        this.width = width;
        this.height = height;
        this.count = count;
        this.type = type;
    }

    public Propertie(Integer id, Component idComponent, String name, Double lenght, Double width, Double height, Integer count, Integer type) {
        this.id = id;
        this.idComponent = idComponent;
        this.name = name;
        this.lenght = lenght;
        this.width = width;
        this.height = height;
        this.count = count;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Component getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(Component idComponent) {
        this.idComponent = idComponent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLenght() {
        return lenght;
    }

    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Propertie[" +
                "component= " + idComponent +
                ", name= " + name +
                ", type= " + type +
                ']';
    }
}
