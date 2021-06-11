package org.shtiroy.factory.controller;

import org.shtiroy.factory.entity.Employee;
import org.shtiroy.factory.entity.Role;
import org.shtiroy.factory.entity.User;
import org.shtiroy.factory.repository.EmployeeRepository;
import org.shtiroy.factory.repository.RoleRepository;
import org.shtiroy.factory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/admin")
    public String adminGet(@ModelAttribute("fr") String fragment, Model model){


        if (fragment.equals("user")) {
            List<User> users = userRepository.findAllByUserActive(true);
            model.addAttribute("fr","fragments/user_form");
            model.addAttribute("users",users);
        }

        if (fragment.equals("employee")){
            List<Employee> employees = employeeRepository.findAllByWorks(true);
            model.addAttribute("fr","fragments/employee_form");
            model.addAttribute("employees", employees);
        }


        return "admin";
    }

    @GetMapping("/admin/user_page")
    public String adminNewUser(Model model){

        List<Role> roles = roleRepository.findAll();

        model.addAttribute("roles", roles);

        return "admin/user_page";

    }

    @PostMapping("/admin/user_page")
    public String createNewUser(@ModelAttribute User user){
        user.setUserActive(true);
        user.setCreatedOn(new Date(System.currentTimeMillis()));
        user.setUserPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/admin?fr=user";
    }

    @PostMapping("/admin/useredit")
    public String userEdit(@ModelAttribute("userId") String userId, Model model){

        User user = userRepository.findById(Long.valueOf(userId)).get();
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("userEdit",user);
        model.addAttribute("roles", roles);

        return "admin/user_edit_page";
    }

    @PostMapping("/admin/useredit/save")
    public String userEditSave(@ModelAttribute User user){
        if (user.getUserPassword().equals("")) {
            userRepository.updateUser(user.getUserRole().getId(),user.getLastName(), user.getFirstName(), user.getEmail(), user.getId());
        } else {
            user.setUserPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.updateUserWithPassword(user.getUserPassword(), user.getUserRole().getId(),user.getLastName(), user.getFirstName(), user.getEmail(), user.getId());
        }

        return "redirect:/admin?fr=user";
    }

    @PostMapping("/admin/userdelete")
    public String userDelete(@ModelAttribute("userId") String userId){

        userRepository.userDeactivation(Long.valueOf(userId));

        return "redirect:/admin?fr=user";
    }
}
