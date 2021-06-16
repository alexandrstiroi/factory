package org.shtiroy.factory.controller;

import org.shtiroy.factory.entity.Employee;
import org.shtiroy.factory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/admin/employee_detail")
    public String adminNewEmployee(){

        return "admin/employee_detail";
    }

    @PostMapping("/admin/employee_detail")
    public String createNewEmployee(@ModelAttribute Employee employee){

        employee.setWorks(true);
        employeeRepository.save(employee);

        return "redirect:/admin?fr=employee";
    }

    @PostMapping("/admin/employee_detail_edit")
    public String employeeEdit(@ModelAttribute("employeeId") String employeeId, Model model){

        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).get();

        model.addAttribute("employeeEdit", employee);

        return "admin/employee_detail_edit";
    }

    @PostMapping("/admin/employee_detail_edit/save")
    public String employeeEditSave(@ModelAttribute Employee employee){

        employeeRepository.updateEmployee(employee.getFirstName(), employee.getLastName(),employee.getMiddleName(),employee.getDateBirth(), employee.getId());

        return "redirect:/admin?fr=employee";
    }

    @PostMapping("/admin/employee/delete")
    public String employeeDelete(@ModelAttribute("employeeId") String employeeId){

        employeeRepository.employeeDeactivation(Long.valueOf(employeeId));

        return "redirect:/admin?fr=employee";
    }
}
