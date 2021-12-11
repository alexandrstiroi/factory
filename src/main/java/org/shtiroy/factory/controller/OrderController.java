package org.shtiroy.factory.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shtiroy.factory.entity.*;
import org.shtiroy.factory.entity.Module;
import org.shtiroy.factory.repository.*;
import org.shtiroy.factory.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.List;

@Controller
public class OrderController {

    public static final Logger LOGGER = LogManager.getLogger(OrderController.class.getName());
    @Autowired
    public ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserLogRepository userLogRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ModuleTypeRepository moduleTypeRepository;
    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private ConsumableRepository consumableRepository;

    @GetMapping("/order/order_list")
    public String orderList(Model model){
        LOGGER.info("get order lost");
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders",orders);
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

    @PostMapping("/order/order_save")
    public String orderSave(@ModelAttribute("order") String orderBody, Authentication authentication){
        LOGGER.info("call orderSave");
        JSONObject jsonObject = new JSONObject(orderBody);
        JSONObject jsonClient = jsonObject.getJSONObject("client");
        JSONObject jsonOrder = jsonObject.getJSONObject("order");
        LOGGER.info("save client info");
        Client client = new Client();
        client.setClientName(jsonClient.getString("client_name"));
        client.setClientAddress(jsonClient.getString("client_address"));
        client.setClientPhone(jsonClient.getString("client_phone"));
        client = clientRepository.save(client);
        LOGGER.info("save order info");
        Order order = new Order();
        order.setNumberFactory(jsonOrder.getString("number_factory"));
        order.setNumberShop(jsonOrder.getString("number_shop"));
        order.setDateRegistration(DateUtils.strToDate(jsonOrder.getString("date_registration")));
        order.setDateProduction(DateUtils.strToDate(jsonOrder.getString("date_production")));
        order.setSupplement(jsonOrder.getString("supplement"));
        order.setIdShop(shopRepository.findById(jsonOrder.getInt("id_shop")).get());
        order.setIdClient(client);
        order = orderRepository.save(order);
        LOGGER.info("save order_product");
        Product product = productRepository.getOne(jsonObject.getInt("id"));
        OrderProduct orderProduct = new OrderProduct(order, product);
        orderProduct = orderProductRepository.save(orderProduct);
        LOGGER.info("save modules to order composition");
        Composition composition = null;
        JSONArray moduleTypeList = jsonObject.getJSONArray("moduleTypeList");
        for (int i = 0; i < moduleTypeList.length(); i++){
            Module module = moduleRepository.findById(moduleTypeList.getJSONObject(i).getInt("idModule")).get();
            ModuleType moduleType = moduleTypeRepository.findById(moduleTypeList.getJSONObject(i).getInt("id")).get();
            composition = new Composition(orderProduct, module,null,null,moduleType);
            compositionRepository.save(composition);
        }
        JSONArray accessoryList = jsonObject.getJSONArray("accessoryList");
        for (int i = 0; i < accessoryList.length(); i++){
            Module module = moduleRepository.findById(accessoryList.getJSONObject(i).getInt("idModule")).get();
            Accessory accessory = accessoryRepository.findById(accessoryList.getJSONObject(i).getInt("id")).get();
            composition = new Composition(orderProduct, module,accessory,null,null);
            compositionRepository.save(composition);
        }
        JSONArray consumableList = jsonObject.getJSONArray("consumableList");
        for (int i = 0; i < consumableList.length(); i++){
            Module module = moduleRepository.findById(consumableList.getJSONObject(i).getInt("idModule")).get();
            Consumable consumable = consumableRepository.findById(consumableList.getJSONObject(i).getInt("id")).get();
            composition = new Composition(orderProduct, module,null,consumable,null);
            compositionRepository.save(composition);
        }
        LOGGER.info(product.toString());
        UserLog userLog = new UserLog(authentication,"SAVE ORDER",orderBody);
        userLogRepository.save(userLog);
        return "/order/order_list";
    }
}
