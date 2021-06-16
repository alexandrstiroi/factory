package org.shtiroy.factory.controller;

import org.shtiroy.factory.entity.Employee;
import org.shtiroy.factory.entity.Role;
import org.shtiroy.factory.entity.User;
import org.shtiroy.factory.entity.UserLog;
import org.shtiroy.factory.repository.EmployeeRepository;
import org.shtiroy.factory.repository.RoleRepository;
import org.shtiroy.factory.repository.UserLogRepository;
import org.shtiroy.factory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @GetMapping("/admin")
    public String adminGet(@ModelAttribute("fr") String fragment, Model model){


        if (fragment.equals("user")) {
            List<User> users = userRepository.findAllByUserActive(true);
            model.addAttribute("fr","fragments/user");
            model.addAttribute("users",users);
        }

        if (fragment.equals("employee")){
            List<Employee> employees = employeeRepository.findAllByWorks(true);
            model.addAttribute("fr","fragments/employee");
            model.addAttribute("employees", employees);
        }


        return "admin";
    }

    @GetMapping("/admin/user_detail")
    public String adminNewUser(Model model){

        List<Role> roles = roleRepository.findAll();

        model.addAttribute("roles", roles);

        return "admin/user_detail";

    }

    @PostMapping("/admin/user_detail")
    public String createNewUser(@ModelAttribute User user, Authentication authentication){
        user.setUserActive(true);
        user.setCreatedOn(new Date(System.currentTimeMillis()));
        user.setUserPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        UserLog userLog = new UserLog(authentication,"CREATE NEW USER",user.toString());
        userLogRepository.save(userLog);
        return "redirect:/admin?fr=user";
    }

    @PostMapping("/admin/useredit")
    public String userEdit(@ModelAttribute("userId") String userId, Model model){

        User user = userRepository.findById(Long.valueOf(userId)).get();
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("userEdit",user);
        model.addAttribute("roles", roles);

        return "admin/user_detail_edit";
    }

    @PostMapping("/admin/useredit/save")
    public String userEditSave(@ModelAttribute User user, Authentication authentication){
        if (user.getUserPassword().equals("")) {
            userRepository.updateUser(user.getUserRole().getId(),user.getLastName(), user.getFirstName(), user.getEmail(), user.getId());
        } else {
            user.setUserPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.updateUserWithPassword(user.getUserPassword(), user.getUserRole().getId(),user.getLastName(), user.getFirstName(), user.getEmail(), user.getId());
        }

        UserLog userLog = new UserLog(authentication,"EDIT USER",user.toString());
        userLogRepository.save(userLog);
        return "redirect:/admin?fr=user";
    }

    @PostMapping("/admin/userdelete")
    public String userDelete(@ModelAttribute("userId") String userId, Authentication authentication){

        userRepository.userDeactivation(Long.valueOf(userId));
        User user = userRepository.findById(Long.valueOf(userId)).get();
        UserLog userLog = new UserLog(authentication,"DELETE USER",user.toString());
        userLogRepository.save(userLog);
        return "redirect:/admin?fr=user";
    }
}
