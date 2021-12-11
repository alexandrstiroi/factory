package org.shtiroy.factory.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(schema = "working_data", name = "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number_shop")
    private String numberShop;

    @Column(name = "number_factory")
    private String numberFactory;

    @Column(name = "date_registration")
    private Date dateRegistration;

    @Column(name = "date_production")
    private Date dateProduction;

    @Column(name = "date_delivery")
    private Date dateDelivery;

    @Column(name = "supplement")
    private String supplement;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "id_shop")
    private Shop idShop;

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "id_client")
    private Client idClient;

    public Order() {
    }

    public Order(String numberShop, String numberFactory, Date dateRegistration, Date dateProduction, Date dateDelivery, String supplement) {
        this.numberShop = numberShop;
        this.numberFactory = numberFactory;
        this.dateRegistration = dateRegistration;
        this.dateProduction = dateProduction;
        this.dateDelivery = dateDelivery;
        this.supplement = supplement;
    }

    public Order(Integer id, String numberShop, String numberFactory, Date dateRegistration, Date dateProduction, Date dateDelivery, String supplement) {
        this.id = id;
        this.numberShop = numberShop;
        this.numberFactory = numberFactory;
        this.dateRegistration = dateRegistration;
        this.dateProduction = dateProduction;
        this.dateDelivery = dateDelivery;
        this.supplement = supplement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumberShop() {
        return numberShop;
    }

    public void setNumberShop(String numberShop) {
        this.numberShop = numberShop;
    }

    public String getNumberFactory() {
        return numberFactory;
    }

    public void setNumberFactory(String numberFactory) {
        this.numberFactory = numberFactory;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Date getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(Date dateProduction) {
        this.dateProduction = dateProduction;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public Shop getIdShop() {
        return idShop;
    }

    public void setIdShop(Shop idShop) {
        this.idShop = idShop;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Order [" +
                " numberShop='" + numberShop +
                ", numberFactory= " + numberFactory +
                ", dateRegistration= " + dateRegistration +
                ", dateProduction= " + dateProduction +
                "]";
    }
}
