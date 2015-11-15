package com.spoton.esm.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.spoton.esm.model.Skill;
import com.spoton.esm.service.SkillService;

@Component
public class SkillIdToSkillConverter implements Converter<Object, Skill> {

	@Autowired
	SkillService skillService;

	public Skill convert(Object element) {
		Integer id = Integer.parseInt((String) element);
		Skill skill = skillService.getSkillById(id);
		return skill;
	}

}