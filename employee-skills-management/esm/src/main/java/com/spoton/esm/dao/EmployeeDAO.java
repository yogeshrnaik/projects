package com.spoton.esm.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spoton.esm.model.Employee;
import com.spoton.esm.model.Skill;

@Repository
public class EmployeeDAO {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public void addEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(employee);
	}

	public void updateEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(employee);
	}

	@SuppressWarnings("unchecked")
	public List<Employee> listEmployees() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Employee> employeesSkillsList = session.createQuery(
				"from Employee e join fetch e.skills order by e.firstName, e.lastName asc").list();
		return employeesSkillsList.stream().distinct().collect(Collectors.toList());
	}

	public Employee getEmployeeById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee employee = (Employee) session.load(Employee.class, new Integer(id));
		if (employee != null) {
			Hibernate.initialize(employee.getSkills());
		}
		return employee;
	}

	public void removeEmployee(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee employee = (Employee) session.load(Employee.class, new Integer(id));
		if (null != employee) {
			session.delete(employee);
		}
	}

	public List<Employee> searchEmployeeBySkill(Skill skill) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "select e from Employee e join fetch e.skills s where upper(s.name) like :skill order by e.firstName, e.lastName asc";
		Query query = session.createQuery(hql);
		query.setParameter("skill", "%" + skill.getName().toUpperCase() + "%");
		List<Employee> employeesSkillsList = query.list();
		return employeesSkillsList.stream().distinct().collect(Collectors.toList());
	}
}
