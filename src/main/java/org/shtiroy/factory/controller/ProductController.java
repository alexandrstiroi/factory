package org.shtiroy.factory.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shtiroy.factory.entity.*;
import org.shtiroy.factory.entity.Module;
import org.shtiroy.factory.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProductController {

    public static final Logger LOGGER = LogManager.getLogger(ProductController.class);

    @Autowired
    private UserLogRepository userLogRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private PropertieRepository propertieRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ModuleTypeRepository moduleTypeRepository;

    @GetMapping("/admin/product_detail")
    public String adminNewProduct(){
        return "admin/product_detail";
    }

    @GetMapping("admin/product_add")
    public String getProductAddPage(){
        return "admin/product_add";
    }

    @PostMapping("/admin/product_add")
    public String adminSaveProduct(@ModelAttribute("product") String product, Authentication authentication){
        Product productObj = new Product();

        JSONObject jsonObject = new JSONObject(product);

        productObj.setName(jsonObject.getString("name"));
        productObj.setStatus(true);

        productObj = productRepository.save(productObj);

        JSONArray jsonArray = jsonObject.optJSONArray("modules");
        for (int i = 0; i < jsonArray.length(); i++) {
            Module module = new Module(productObj, jsonArray.getJSONObject(i).getString("name"),
                    jsonArray.getJSONObject(i).getBoolean("unique"),
                    jsonArray.getJSONObject(i).getInt("isAddition"));
            Module moduleSaved = moduleRepository.save(module);
            for (int x = 0; x < jsonArray.getJSONObject(i).getJSONArray("moduleTypes").length(); x++) {
                
            }
        }
        return "redirect:/admin?fr=product";
    }
    @PostMapping("/admin/product_detail")
    public String adminSaveNewProduct(@ModelAttribute("product") String product, Authentication authentication){

        Product productObj = new Product();

        JSONObject jsonObject = new JSONObject(product);

        productObj.setName(jsonObject.getString("name"));
        productObj.setStatus(true);

        productObj = productRepository.save(productObj);

        JSONArray jsonArray = jsonObject.optJSONArray("components");
        for (int i = 0; i < jsonArray.length(); i++){

            Component component = new Component(productObj,jsonArray.getJSONObject(i).getString("name"),-1);
            component = componentRepository.save(component);
            for(int x = 0; x < jsonArray.getJSONObject(i).getJSONArray("properties").length(); x++){


                Propertie propertie = new Propertie();
                String name = jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("name");
                Double lenght = 0.0, width = 0.0, height = 0.0;

                if (jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("name").equals("")) {
                    propertie.setName(null);
                } else {
                    propertie.setName(jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("name"));
                }

                propertie.setIdComponent(component);

                if (jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("lenght").equals("")) {
                    propertie.setLenght(null);
                } else {
                    propertie.setLenght(Double.parseDouble(jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("lenght")));
                }

                if (jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("width").equals("")) {
                    propertie.setWidth(null);
                } else {
                    propertie.setWidth(Double.parseDouble(jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("width")));
                }

                if (jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("height").equals("")){
                    propertie.setHeight(null);
                } else {
                    propertie.setHeight(Double.parseDouble(jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("height")));
                }
                if (jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("count").equals("")) {
                    propertie.setCount(null);
                } else {
                    propertie.setCount(Integer.parseInt(jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("count")));
                }

                propertie.setType(Integer.parseInt(jsonArray.getJSONObject(i).getJSONArray("properties").getJSONObject(x).getString("type")));

                propertie = propertieRepository.save(propertie);
            }
        }

        UserLog userLog = new UserLog(authentication,"CREATE NEW PRODUCT",product);
        userLogRepository.save(userLog);

        return "redirect:/admin?fr=product";
    }

    @PostMapping("/admin/pr_in")
    public String adminSavePr(@ModelAttribute("product") String product, Authentication authentication){
        LOGGER.info("start save product");
        Product productObj = new Product();
        JSONObject jsonObject = new JSONObject(product);
        productObj.setName(jsonObject.getString("name"));
        productObj.setStatus(true);
        productObj = productRepository.save(productObj);
        JSONArray moduleArray = jsonObject.optJSONArray("modules");
        for (int i = 0; i < moduleArray.length(); i++) {
            Module module = new Module(productObj, moduleArray.getJSONObject(i).getString("name"),
                                                   moduleArray.getJSONObject(i).getBoolean("unique"),
                    moduleArray.getJSONObject(i).getInt("isAddition"));
            module = moduleRepository.save(module);

            for(int x = 0; x < moduleArray.getJSONObject(i).getJSONArray("moduleTypes").length(); x++){
                ModuleType moduleType = new ModuleType();
                String name = moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("name");
                Double depth = 0.0, width = 0.0, height = 0.0;

                if (moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("name").equals("")) {
                    moduleType.setModuleTypeName(null);
                } else {
                    moduleType.setModuleTypeName(moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("name"));
                }

                moduleType.setIdModule(module);

                if (moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("depth").equals("")) {
                    moduleType.setDepth(null);
                } else {
                    moduleType.setDepth(Integer.parseInt(moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("depth")));
                }

                if (moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("width").equals("")) {
                    moduleType.setWidth(null);
                } else {
                    moduleType.setWidth(Integer.parseInt(moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("width")));
                }

                if (moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("height").equals("")){
                    moduleType.setHeight(null);
                } else {
                    moduleType.setHeight(Integer.parseInt(moduleArray.getJSONObject(i).getJSONArray("moduleTypes").getJSONObject(x).getString("height")));
                }
                moduleType = moduleTypeRepository.save(moduleType);
            }
        }
        LOGGER.info("end save product");
        return "redirect:/admin?fr=product";
    }
}
