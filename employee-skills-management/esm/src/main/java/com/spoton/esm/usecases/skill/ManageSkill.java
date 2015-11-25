package com.spoton.esm.usecases.skill;

import com.spoton.esm.model.Skill;
import com.spoton.esm.service.SkillService;
import com.spoton.esm.usecases.common.Request;
import com.spoton.esm.usecases.common.UseCase;

public abstract class ManageSkill extends UseCase {

	private SkillService skillService;

	public void execute(Request request) {
		checkIfSkillAlreadyExists(((SkillRequest) request).getSkill());
	}

	protected void checkIfSkillAlreadyExists(Skill skill) {
		Skill existingSkill = skillService.getSkillByName(skill.getName());
		if (existingSkill != null) {
			throw new SkillAlreadyExists(existingSkill);
		}
	}

}
