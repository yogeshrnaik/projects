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
		this.sessionFactory.getCurrentSession().persist(skill);
	}

	public void updateSkill(Skill skill) {
		this.sessionFactory.getCurrentSession().update(skill);
	}

	@SuppressWarnings("unchecked")
	public List<Skill> listSkills() {
		return this.sessionFactory.getCurrentSession().createQuery("from Skill order by name asc").list();
	}

	public Skill getSkillById(int id) {
		return (Skill) this.sessionFactory.getCurrentSession().load(Skill.class, new Integer(id));
	}

	public void removeSkill(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Skill s = (Skill) session.load(Skill.class, new Integer(id));
		if (null != s) {
			session.delete(s);
		}
	}
}
