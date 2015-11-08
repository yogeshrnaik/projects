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

import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonService;

@Controller
public class PersonController {

	@Autowired(required = true)
	private PersonService personService;

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public String listPersons(Model model) {
		return listPersons(new Person(), model);
	}

	// For add and update person both
	@RequestMapping(value = "/person/add", method = RequestMethod.POST)
	public String addPerson(@Valid @ModelAttribute("person") Person p, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			if (p.getId() == 0) {
				// new person, add it
				this.personService.addPerson(p);
			} else {
				// existing person, call update
				this.personService.updatePerson(p);
			}
			return "redirect:/persons";
		} else {
			return listPersons(p, model);
		}
	}

	@RequestMapping("/remove/{id}")
	public String removePerson(@PathVariable("id") int id) {
		this.personService.removePerson(id);
		return "redirect:/persons";
	}

	@RequestMapping("/edit/{id}")
	public String editPerson(@PathVariable("id") int id, Model model) {
		model.addAttribute("person", this.personService.getPersonById(id));
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	
	private String listPersons(Person p, Model model) {
		model.addAttribute("person", p);
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
}
