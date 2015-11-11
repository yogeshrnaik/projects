package com.spoton.esm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoton.esm.dao.EmployeeDAO;
import com.spoton.esm.dto.EmployeeSearchDto;
import com.spoton.esm.model.Employee;
import com.spoton.esm.model.Skill;

@Service
public class EmployeeService {

	@Autowired(required = true)
	private EmployeeDAO employeeDAO;

	@Transactional
	public void addEmployee(Employee employee) {
		this.employeeDAO.addEmployee(employee);
	}

	@Transactional
	public void updateEmployee(Employee employee) {
		this.employeeDAO.updateEmployee(employee);
	}

	@Transactional
	public List<Employee> listEmployees() {
		return this.employeeDAO.listEmployees();
	}

	@Transactional
	public Employee getEmployeeById(int id) {
		return this.employeeDAO.getEmployeeById(id);
	}

	@Transactional
	public void deleteEmployee(int id) {
		this.employeeDAO.removeEmployee(id);
	}

	@Transactional
	public List<Employee> searchEmployeeBySkill(EmployeeSearchDto dto) {
		return employeeDAO.searchEmployeeBySkill(dto);
	}

}
