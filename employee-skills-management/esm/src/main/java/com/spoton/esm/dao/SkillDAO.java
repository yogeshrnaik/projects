package com.spoton.esm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spoton.esm.model.Skill;

@Repository
public class SkillDAO {
	private static final Logger logger = LoggerFactory.getLogger(SkillDAO.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public void addSkill(Skill skill) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(skill);
		logger.info("Skill saved successfully, Skill Details=" + skill);
	}

	public void updateSkill(Skill skill) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(skill);
		logger.info("Skill updated successfully, Skill Details=" + skill);
	}

	@SuppressWarnings("unchecked")
	public List<Skill> listSkills() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Skill> SkillsList = session.createQuery("from Skill order by name asc").list();
		for (Skill p : SkillsList) {
			logger.info("Skill List::" + p);
		}
		return SkillsList;
	}

	public Skill getSkillById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Skill p = (Skill) session.load(Skill.class, new Integer(id));
		logger.info("Skill loaded successfully, Skill details=" + p);
		return p;
	}

	public void removeSkill(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Skill p = (Skill) session.load(Skill.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Skill deleted successfully, Skill details=" + p);
	}
}
