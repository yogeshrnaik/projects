package com.spoton.esm.usecases.skill;

import com.spoton.esm.service.SkillService;
import com.spoton.esm.usecases.common.Request;

public class EditSkill extends ManageSkill {

	private SkillService skillService;

	public EditSkill(SkillService skillService) {
		this.skillService = skillService;
	}

	public void execute(Request req) {
		super.execute(req);

		SkillRequest editSkillReq = (SkillRequest) req;
		skillService.updateSkill(editSkillReq.getSkill());
	}

}
