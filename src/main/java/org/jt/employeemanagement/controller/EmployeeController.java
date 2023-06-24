package org.jt.employeemanagement.controller;

import java.io.FileOutputStream;
import java.util.UUID;

import org.jt.employeemanagement.model.Employee;
import org.jt.employeemanagement.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping({ "/", "/viewemployee" })
    public String viewEmployee(Model model) {
        var employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);

        return "view-employee";
    }

    @GetMapping("/addemployee")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute Employee employee,
            @RequestParam("picture") MultipartFile multipartFile) throws Exception {

        if (!multipartFile.isEmpty()) {
            var fileName = multipartFile.getOriginalFilename();
            var fileData = multipartFile.getBytes();

            var createFileName = UUID.randomUUID().toString() + "-" + fileName;
            var location = "images/" + createFileName;

            employee.setPictureURL("/" + location);

            var fileOutputStream = new FileOutputStream(location);
            fileOutputStream.write(fileData);
            fileOutputStream.close();
        }
        employeeService.saveEmployee(employee);
        return "redirect:/viewemployee";
    }

    @GetMapping("/remove-employee")
    public String removeEmployee(@RequestParam String id) {
        employeeService.removeEmployeeById(id);

        return "redirect:/";
    }

    @GetMapping("/update-employee")
    public String updateEmployee(@RequestParam String id, Model model) {
        var employee = employeeService.employeeById(id).orElse(new Employee());
        model.addAttribute("employee", employee);
        return "add-employee";
    }
}
