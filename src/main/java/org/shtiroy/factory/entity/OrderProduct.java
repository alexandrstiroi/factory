package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "id_order")
    private Order idOrder;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "id_product")
    private Product idProduct;

    public OrderProduct() {
    }

    public OrderProduct(Order idOrder, Product idProduct) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
    }

    public OrderProduct(Integer id, Order idOrder, Product idProduct) {
        this.id = id;
        this.idOrder = idOrder;
        this.idProduct = idProduct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Order idOrder) {
        this.idOrder = idOrder;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return "OrderProduct[" +
                " order=" + idOrder +
                ", product=" + idProduct +
                ']';
    }
}
