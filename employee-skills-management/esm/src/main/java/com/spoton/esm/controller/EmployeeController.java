package com.spoton.esm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spoton.esm.exception.ESMException;
import com.spoton.esm.model.Employee;
import com.spoton.esm.service.EmployeeService;
import com.spoton.esm.service.SkillService;

@Controller
public class EmployeeController {

	@Autowired
	private MessageSource msgResource;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SkillService skillService;

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
	public ModelAndView showEditEmployee(@PathVariable("id") int id, Model model) throws ESMException {
		Employee employee = this.employeeService.getEmployeeById(id);
		if (employee == null) {
			throw new ESMException(msgResource.getMessage("employee.not.found", new Integer[] { id }, null));
		}
		return showEditEmployee(employee, model);
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public ModelAndView addEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model,
			RedirectAttributes redirectAttr) {
		if (!result.hasErrors()) {
			this.employeeService.addEmployee(employee);
			redirectAttr.addFlashAttribute("message", msgResource.getMessage("employee.add.success", null, null));
			return new ModelAndView("redirect:/employees");
		} else {
			return showAddEmployee(employee, model);
		}
	}

	@RequestMapping(value = "/editEmployee", method = RequestMethod.POST)
	public ModelAndView editEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
			Model model, RedirectAttributes redirectAttr) {
		if (!result.hasErrors()) {
			this.employeeService.updateEmployee(employee);
			redirectAttr.addFlashAttribute("message", msgResource.getMessage("employee.edit.success", null, null));
			return new ModelAndView("redirect:/employees");
		} else {
			return showEditEmployee(employee, model);
		}
	}

	@RequestMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") int id, RedirectAttributes redirectAttr) {
		employeeService.deleteEmployee(id);
		redirectAttr.addFlashAttribute("message", msgResource.getMessage("employee.delete.success", null, null));
		return "redirect:/employees";
	}

	private ModelAndView showAddEmployee(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		model.addAttribute("allSkills", skillService.listSkills());
		return new ModelAndView("showAddEmployee");
	}

	private ModelAndView showEditEmployee(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		model.addAttribute("allSkills", skillService.listSkills());
		return new ModelAndView("showEditEmployee");
	}

}
