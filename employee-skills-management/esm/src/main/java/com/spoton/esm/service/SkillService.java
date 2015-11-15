package com.spoton.esm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoton.esm.dao.SkillDAO;
import com.spoton.esm.model.Skill;

@Service
public class SkillService {

	@Autowired(required = true)
	private SkillDAO skillDAO;

	@Transactional
	public void addSkill(Skill skill) {
		this.skillDAO.addSkill(skill);
	}

	@Transactional
	public void updateSkill(Skill skill) {
		this.skillDAO.updateSkill(skill);
	}

	@Transactional
	public List<Skill> listSkills() {
		return this.skillDAO.listSkills();
	}

	@Transactional
	public Skill getSkillById(int id) {
		return this.skillDAO.getSkillById(id);
	}

	@Transactional
	public Skill getSkillByName(String name) {
		return this.skillDAO.getSkillByName(name);
	}

	@Transactional
	public void deleteSkill(int id) {
		this.skillDAO.removeSkill(id);
	}

}
