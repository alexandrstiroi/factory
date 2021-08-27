package org.shtiroy.factory.controller;

import org.shtiroy.factory.entity.Shop;
import org.shtiroy.factory.entity.Product;
import org.shtiroy.factory.repository.ShopRepository;
import org.shtiroy.factory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    public ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/order/order_list")
    public String orderList(){

        return "/order/order_list";
    }

    @GetMapping("/order/order_add")
    public String openOrderAdd(Model model){

        List<Shop> shops = shopRepository.findByShopType(1);
        List<Product> products = productRepository.findAllByStatus(true);

        model.addAttribute("shops", shops);
        model.addAttribute("products",products);
        return "/order/order_add";
    }


}
