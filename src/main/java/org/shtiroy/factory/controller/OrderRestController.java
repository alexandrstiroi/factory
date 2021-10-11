package org.shtiroy.factory.controller;


import org.shtiroy.factory.entity.Component;
import org.shtiroy.factory.entity.Shop;
import org.shtiroy.factory.entity.Product;
import org.shtiroy.factory.entity.Propertie;
import org.shtiroy.factory.repository.ComponentRepository;
import org.shtiroy.factory.repository.ShopRepository;
import org.shtiroy.factory.repository.ProductRepository;
import org.shtiroy.factory.repository.PropertieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PropertieRepository propertieRepository;

    @PostMapping("/order/order_add/getShop")
    public Shop getShopById(@ModelAttribute("shopId") String shopId){
        System.out.println("shopId" + shopId);
        Shop shop = shopRepository.findById(Integer.valueOf(shopId)).get();

        return shop;
    }

    @PostMapping("/order/order_add/getComponents")
    public List<Component> getComponents(@ModelAttribute("productId") String productId){
        Product product = productRepository.findById(Integer.valueOf(productId)).get();
        return componentRepository.findByIdProduct(product);


    }

    @PostMapping("/order/order_add/getProperties")
    public List<Propertie> getProperties(@ModelAttribute("componentId") String componentId){

        Component component = componentRepository.findById(Integer.valueOf(componentId)).get();
        return propertieRepository.findByIdComponent(component);

    }

}
