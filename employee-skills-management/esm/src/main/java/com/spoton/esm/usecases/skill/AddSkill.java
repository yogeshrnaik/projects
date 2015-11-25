package com.spoton.esm.usecases.skill;

import com.spoton.esm.service.SkillService;
import com.spoton.esm.usecases.common.Request;

public class AddSkill extends ManageSkill {

	private SkillService skillService;

	public AddSkill(SkillService skillService) {
		this.skillService = skillService;
	}

	public void execute(Request req) {
		super.execute(req);

		SkillRequest addSkillReq = (SkillRequest) req;
		skillService.addSkill(addSkillReq.getSkill());
	}

}
