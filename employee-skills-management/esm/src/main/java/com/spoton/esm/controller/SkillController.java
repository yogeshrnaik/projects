package com.spoton.esm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SkillController {

	@RequestMapping(value = "/skills", method = RequestMethod.GET)
	public ModelAndView showSkills(Model model) {
		return new ModelAndView("skills");
	}

}
