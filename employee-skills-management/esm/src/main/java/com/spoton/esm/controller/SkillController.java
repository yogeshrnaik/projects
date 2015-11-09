package com.spoton.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spoton.esm.service.SkillService;

@Controller
public class SkillController {

	@Autowired
	private SkillService skillService;

	@RequestMapping(value = "/skills", method = RequestMethod.GET)
	public ModelAndView showSkills(Model model) {
		model.addAttribute("skills", this.skillService.listSkills());
		return new ModelAndView("skills");
	}

}
