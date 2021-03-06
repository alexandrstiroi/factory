package org.shtiroy.factory.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shtiroy.factory.entity.*;
import org.shtiroy.factory.entity.Module;
import org.shtiroy.factory.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class OrderRestController {

    private Logger LOGGER = LogManager.getLogger(OrderRestController.class);
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PropertieRepository propertieRepository;
    @Autowired
    private UserLogRepository userLogRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ModuleTypeRepository moduleTypeRepository;
    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private ConsumableRepository consumableRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Value("${factory.files}")
    private String filesPath;

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

    @PostMapping("/order/order_add/getModules")
    public List<Module> getModules(@ModelAttribute("productId") String productId, Authentication authentication){
        LOGGER.info("get modules");
        Product product = productRepository.findById(Integer.valueOf(productId)).get();
        UserLog userLog = new UserLog(authentication,"GET MODULES",product.toString());
        userLogRepository.save(userLog);
        return moduleRepository.findByIdProduct(product);
    }

    @PostMapping("/order/order_add/getModuleTypes")
    public List<ModuleType> getModuleTypes(@ModelAttribute("idModule") String idModule, Authentication authentication){
        LOGGER.info("get moduleTypes");
        Module module = moduleRepository.findById(Integer.valueOf(idModule)).get();
        UserLog userLog = new UserLog(authentication,"GET MODULETYPE",module.toString());
        userLogRepository.save(userLog);
        return moduleTypeRepository.findByIdModule(module);
    }

    @PostMapping("/order/order_add/getAccessories")
    public List<Accessory> getAccessories(Authentication authentication){
        LOGGER.info("get accessories");
        UserLog userLog = new UserLog(authentication,"GET ACCESSORIES","ALL ACCESSORIES");
        userLogRepository.save(userLog);
        return accessoryRepository.findAll();
    }

    @PostMapping("/order/order_add/getConsumable")
    public List<Consumable> getConsumable(@ModelAttribute("idModule") String idModule, @ModelAttribute("moduleIsAddition") String moduleIsAddition,  Authentication authentication){
        LOGGER.info("get consumable");
        UserLog userLog = new UserLog(authentication,"GET CONSUMABLE","idModule=" + idModule+"@moduleIsAddition="+moduleIsAddition);
        userLogRepository.save(userLog);
        return consumableRepository.findByConsumableTypeOrderByConsumableCatAscConsumableName(Integer.parseInt(moduleIsAddition));
    }

    @PostMapping("/admin/product_detail/saveImage")
    public Photo saveImage(@ModelAttribute("image") MultipartFile image, @ModelAttribute("folderName") String folderName,
                           Authentication authentication){
        LOGGER.info("CALL saveImage()");
        String strFolder = filesPath+"images/"+folderName;
        if (Files.notExists(Paths.get(strFolder))){
            LOGGER.info("make dir");
            new File(strFolder).mkdir();
        }
        int countFile = new File(strFolder).list().length;
        Photo photo = new Photo("files\\images\\"+folderName+"\\"+folderName+countFile+".jpeg");
        File file = new File(strFolder+"/"+folderName+countFile+".jpeg");
        try {
            image.transferTo(file);
            LOGGER.info("saved image");
        } catch (IOException | IllegalStateException ex){
            LOGGER.info(ex.getMessage());
        }
        photo = photoRepository.save(photo);
        LOGGER.info("send image path");
        return photo;
    }
}
