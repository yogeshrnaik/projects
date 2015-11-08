package com.journaldev.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonService;

@RestController
public class PersonRestController {

	@Autowired(required = true)
	private PersonService personService;

	@RequestMapping(value = "/rest/people", method = RequestMethod.GET)
	public List<Person> listPersons() {
		return this.personService.listPersons();
	}

	@RequestMapping(value = "/rest/people", method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Person person) {
		personService.addPerson(person);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
