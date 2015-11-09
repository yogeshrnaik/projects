package com.spoton.esm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spoton.esm.model.Employee;
import com.spoton.esm.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public ModelAndView showEmployees(Model model) {
		model.addAttribute("employees", this.employeeService.listEmployees());
		return new ModelAndView("employees");
	}

	@RequestMapping(value = "/showAddEmployee", method = RequestMethod.GET)
	public ModelAndView showAddEmployee(Model model) {
		return showAddEmployee(new Employee(), model);
	}

	@RequestMapping("/showEditEmployee/{id}")
	public ModelAndView showEditPerson(@PathVariable("id") int id, Model model) {
		return showEditEmployee(this.employeeService.getEmployeeById(id), model);
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public ModelAndView addEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			this.employeeService.addEmployee(employee);
			return new ModelAndView("redirect:/employees");
		} else {
			return showAddEmployee(employee, model);
		}
	}

	@RequestMapping(value = "/editEmployee", method = RequestMethod.POST)
	public ModelAndView editEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			this.employeeService.updateEmployee(employee);
			return new ModelAndView("redirect:/employees");
		} else {
			return showEditEmployee(employee, model);
		}
	}

	@RequestMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") int id) {
		employeeService.deleteEmployee(id);
		return "redirect:/employees";
	}

	private ModelAndView showAddEmployee(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return new ModelAndView("showAddEmployee");
	}

	private ModelAndView showEditEmployee(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return new ModelAndView("showEditEmployee");
	}

}
