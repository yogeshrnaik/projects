package com.spoton.esm.usecases.skill;

import com.spoton.esm.model.Skill;

public class SkillAlreadyExists extends RuntimeException {
	private static final long serialVersionUID = -5280292772338708777L;

	public final Skill skill;

	public SkillAlreadyExists(Skill skill) {
		this.skill = skill;
	}
}
