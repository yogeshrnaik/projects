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
import com.spoton.esm.model.Skill;
import com.spoton.esm.service.SkillService;

@Controller
public class SkillController {

	@Autowired
	private MessageSource msgResource;

	@Autowired
	private SkillService skillService;

	@RequestMapping(value = "/skills", method = RequestMethod.GET)
	public ModelAndView showSkills(Model model) {
		model.addAttribute("skills", skillService.listSkills());
		return new ModelAndView("skills");
	}

	@RequestMapping(value = "/showAddSkill", method = RequestMethod.GET)
	public ModelAndView showAddSkill(Model model) {
		return showAddSkill(new Skill(), model);
	}

	@RequestMapping("/showEditSkill/{id}")
	public ModelAndView showEditSkill(@PathVariable("id") int id, Model model) {
		return showEditSkill(skillService.getSkillById(id), model);
	}

	@RequestMapping(value = "/addSkill", method = RequestMethod.POST)
	public ModelAndView addSkill(@Valid @ModelAttribute("skill") Skill skill, BindingResult result, Model model,
			RedirectAttributes redirectAttr) {
		if (!result.hasErrors()) {
			Skill existingSkill = checkIfSkillAlreadyExists(skill);
			if (existingSkill != null) {
				redirectAttr.addFlashAttribute("errorMsg",
						msgResource.getMessage("skill.already.exists", new String[] { skill.getName() }, null));
				return new ModelAndView("redirect:/showAddSkill");
			} else {
				skillService.addSkill(skill);
				redirectAttr.addFlashAttribute("message", msgResource.getMessage("skill.add.success", null, null));
				return new ModelAndView("redirect:/skills");
			}
		} else {
			return showAddSkill(skill, model);
		}
	}

	private Skill checkIfSkillAlreadyExists(Skill skill) {
		return skillService.getSkillByName(skill.getName());
	}

	@RequestMapping(value = "/editSkill", method = RequestMethod.POST)
	public ModelAndView editSkill(@Valid @ModelAttribute("skill") Skill skill, BindingResult result, Model model,
			RedirectAttributes redirectAttr) {
		if (!result.hasErrors()) {
			Skill existingSkill = checkIfSkillAlreadyExists(skill);
			if (existingSkill != null && existingSkill.getId() != skill.getId()) {
				model.addAttribute("errorMsg",
						msgResource.getMessage("skill.already.exists", new String[] { skill.getName() }, null));
				return new ModelAndView("showEditSkill");
			} else {
				skillService.updateSkill(skill);
				redirectAttr.addFlashAttribute("message", msgResource.getMessage("skill.edit.success", null, null));
				return new ModelAndView("redirect:/skills");
			}
		} else {
			return showEditSkill(skill, model);
		}
	}

	@RequestMapping("/deleteSkill/{id}")
	public String deleteSkill(@PathVariable("id") int id, RedirectAttributes redirectAttr) {
		skillService.deleteSkill(id);
		redirectAttr.addFlashAttribute("message", msgResource.getMessage("skill.delete.success", null, null));
		return "redirect:/skills";
	}

	private ModelAndView showAddSkill(Skill skill, Model model) {
		model.addAttribute("skill", skill);
		return new ModelAndView("showAddSkill");
	}

	private ModelAndView showEditSkill(Skill skill, Model model) {
		model.addAttribute("skill", skill);
		return new ModelAndView("showEditSkill");
	}

}
