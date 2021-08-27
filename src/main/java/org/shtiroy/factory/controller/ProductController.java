package org.shtiroy.factory.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shtiroy.factory.entity.Component;
import org.shtiroy.factory.entity.Product;
import org.shtiroy.factory.entity.Propertie;
import org.shtiroy.factory.entity.UserLog;
import org.shtiroy.factory.repository.ComponentRepository;
import org.shtiroy.factory.repository.ProductRepository;
import org.shtiroy.factory.repository.PropertieRepository;
import org.shtiroy.factory.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProductController {

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private PropertieRepository propertieRepository;

    @GetMapping("/admin/product_detail")
    public String adminNewProduct(){
        return "admin/product_detail";
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
}
