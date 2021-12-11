package org.shtiroy.factory.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shtiroy.factory.entity.Accessory;
import org.shtiroy.factory.entity.UserLog;
import org.shtiroy.factory.repository.AccessoryRepository;
import org.shtiroy.factory.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessoriesController {
    public static final Logger LOGGER = LogManager.getLogger(AccessoriesController.class);

    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private UserLogRepository userLogRepository;

    @GetMapping("/admin/accessories_add")
    public String adminAccessoriesAdd(){
        LOGGER.info("get accessories add");
        return "/admin/accessories_add";
    }

    @PostMapping("/admin/accessories_add")
    public String adminAccessoryAdd(@ModelAttribute Accessory accessory, Authentication authentication){
        LOGGER.info("call procedure new accessory");
        accessoryRepository.save(accessory);
        UserLog userLog = new UserLog(authentication,"CREATE NEW ACCESSORY",accessory.toString());
        userLogRepository.save(userLog);
        return "redirect:/admin?fr=accessories";
    }
}
