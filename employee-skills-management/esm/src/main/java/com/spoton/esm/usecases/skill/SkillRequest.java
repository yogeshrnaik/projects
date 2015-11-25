package com.spoton.esm.usecases.skill;

import com.spoton.esm.model.Skill;
import com.spoton.esm.usecases.common.Request;

public class SkillRequest extends Request {
	final Skill skill;

	public SkillRequest(Skill skill) {
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

}