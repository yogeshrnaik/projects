package com.journaldev.spring.controller;

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

import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonService;

@Controller
public class PersonController {

	@Autowired(required = true)
	private PersonService personService;

	@RequestMapping(value = "/people", method = RequestMethod.GET)
	public ModelAndView listPeople(Model model) {
		model.addAttribute("listPersons", this.personService.listPersons());
		return new ModelAndView("listPeople");
	}

	@RequestMapping(value = "/showAddPerson", method = RequestMethod.GET)
	public ModelAndView showAddPerson(Model model) {
		return showAddPerson(new Person(), model);
	}

	@RequestMapping(value = "/person/add", method = RequestMethod.POST)
	public ModelAndView addPerson(@Valid @ModelAttribute("person") Person p, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			this.personService.addPerson(p);
			return new ModelAndView("redirect:/people");
		} else {
			return showAddPerson(p, model);
		}
	}

	@RequestMapping(value = "/person/edit", method = RequestMethod.POST)
	public ModelAndView editPerson(@Valid @ModelAttribute("person") Person p, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			this.personService.updatePerson(p);
			return new ModelAndView("redirect:/people");
		} else {
			return showEditPerson(p, model);
		}
	}

	@RequestMapping("/remove/{id}")
	public String removePerson(@PathVariable("id") int id) {
		this.personService.removePerson(id);
		return "redirect:/people";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditPerson(@PathVariable("id") int id, Model model) {
		return showEditPerson(this.personService.getPersonById(id), model);
	}

	private ModelAndView showAddPerson(Person person, Model model) {
		model.addAttribute("person", person);
		return new ModelAndView("showAddPerson");
	}

	private ModelAndView showEditPerson(Person person, Model model) {
		model.addAttribute("person", person);
		return new ModelAndView("showEditPerson");
	}
}
