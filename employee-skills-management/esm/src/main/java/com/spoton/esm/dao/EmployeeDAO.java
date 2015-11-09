package com.spoton.esm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spoton.esm.model.Employee;

@Repository
public class EmployeeDAO {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public void addEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(employee);
		logger.info("Employee saved successfully, Employee Details=" + employee);
	}

	public void updateEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(employee);
		logger.info("Employee updated successfully, Employee Details=" + employee);
	}

	@SuppressWarnings("unchecked")
	public List<Employee> listEmployees() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Employee> EmployeesList = session.createQuery("from Employee").list();
		for (Employee p : EmployeesList) {
			logger.info("Employee List::" + p);
		}
		return EmployeesList;
	}

	public Employee getEmployeeById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee p = (Employee) session.load(Employee.class, new Integer(id));
		logger.info("Employee loaded successfully, Employee details=" + p);
		return p;
	}

	public void removeEmployee(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee p = (Employee) session.load(Employee.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Employee deleted successfully, Employee details=" + p);
	}
}
