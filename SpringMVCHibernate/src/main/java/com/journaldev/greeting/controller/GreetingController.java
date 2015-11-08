package com.journaldev.greeting.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journaldev.greeting.dto.Greeting;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting/{name}")
	public Greeting greeting(@PathVariable String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));
	}
}