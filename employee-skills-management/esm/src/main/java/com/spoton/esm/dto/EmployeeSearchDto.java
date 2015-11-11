package com.spoton.esm.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeSearchDto {

	@NotEmpty
	@Size(min = 2, max = 50)
	private String skill;

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skillName) {
		this.skill = skillName;
	}
}
