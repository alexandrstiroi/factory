package org.shtiroy.factory.controller;

import org.shtiroy.factory.entity.Employee;
import org.shtiroy.factory.entity.UserLog;
import org.shtiroy.factory.repository.EmployeeRepository;
import org.shtiroy.factory.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @GetMapping("/admin/employee_detail")
    public String adminNewEmployee(){

        return "admin/employee_detail";
    }

    @PostMapping("/admin/employee_detail")
    public String createNewEmployee(@ModelAttribute Employee employee, Authentication authentication){

        employee.setWorks(true);
        employeeRepository.save(employee);

        UserLog userLog = new UserLog(authentication,"CREATE NEW EMPLOYEE",employee.toString());
        userLogRepository.save(userLog);
        return "redirect:/admin?fr=employee";
    }

    @PostMapping("/admin/employee_detail_edit")
    public String employeeEdit(@ModelAttribute("employeeId") String employeeId, Model model){

        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).get();

        model.addAttribute("employeeEdit", employee);

        return "admin/employee_detail_edit";
    }

    @PostMapping("/admin/employee_detail_edit/save")
    public String employeeEditSave(@ModelAttribute Employee employee, Authentication authentication){

        employeeRepository.updateEmployee(employee.getFirstName(), employee.getLastName(),employee.getMiddleName(),employee.getDateBirth(), employee.getId());

        UserLog userLog = new UserLog(authentication,"EDIT EMPLOYEE",employee.toString());
        userLogRepository.save(userLog);

        return "redirect:/admin?fr=employee";
    }

    @PostMapping("/admin/employee/delete")
    public String employeeDelete(@ModelAttribute("employeeId") String employeeId, Authentication authentication){

        employeeRepository.employeeDeactivation(Long.valueOf(employeeId));

        UserLog userLog = new UserLog(authentication,"DELETE EMPLOYEE",employeeId);
        userLogRepository.save(userLog);

        return "redirect:/admin?fr=employee";
    }
}
