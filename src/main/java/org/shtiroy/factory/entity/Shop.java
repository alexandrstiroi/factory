package org.shtiroy.factory.entity;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shop_address")
    private String shopAddress;

    @Column(name = "shop_phone")
    private String shopPhone;

    @Column(name = "shop_type")
    private Integer shopType;

    public Shop() {
    }

    public Shop(Integer id, String shopName, String shopAddress, String shopPhone, Integer shopType) {
        this.id = id;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopPhone = shopPhone;
        this.shopType = shopType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    @Override
    public String toString() {
        return shopName + " " + shopAddress + " " + shopPhone;
    }
}
