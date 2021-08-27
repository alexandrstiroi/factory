package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_composition")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = OrderProduct.class)
    @JoinColumn(name = "id_order_product")
    private OrderProduct idOrderProduct;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "id_product")
    private OrderProduct idProduct;

    @ManyToOne(targetEntity = Component.class)
    @JoinColumn(name = "id_component")
    private OrderProduct idComponent;

    @ManyToOne(targetEntity = Propertie.class)
    @JoinColumn(name = "id_propertie")
    private OrderProduct idPropertie;

    public Composition() {
    }

    public Composition(OrderProduct idOrderProduct, OrderProduct idProduct, OrderProduct idComponent, OrderProduct idPropertie) {
        this.idOrderProduct = idOrderProduct;
        this.idProduct = idProduct;
        this.idComponent = idComponent;
        this.idPropertie = idPropertie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderProduct getIdOrderProduct() {
        return idOrderProduct;
    }

    public void setIdOrderProduct(OrderProduct idOrderProduct) {
        this.idOrderProduct = idOrderProduct;
    }

    public OrderProduct getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(OrderProduct idProduct) {
        this.idProduct = idProduct;
    }

    public OrderProduct getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(OrderProduct idComponent) {
        this.idComponent = idComponent;
    }

    public OrderProduct getIdPropertie() {
        return idPropertie;
    }

    public void setIdPropertie(OrderProduct idPropertie) {
        this.idPropertie = idPropertie;
    }


}
