package com.spoton.esm.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spoton.esm.dto.EmployeeSearchDto;
import com.spoton.esm.model.Employee;

@ContextConfiguration(locations = "classpath:servlet-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeDaoTest {

	@Autowired
	private EmployeeDAO employeeDAO;

	@Test
	@Transactional
	public void testSsearchEmployeeBySkillWithPaging() {
		List<Integer> empIds = employeeDAO.searchEmployeeIdsBySkillWithPaging(new EmployeeSearchDto("pr"), 0, 3);
		empIds.forEach(System.out::println);

		List<Employee> emps = employeeDAO.getEmployeesByIds(empIds);
		emps.forEach(e -> System.out.println(e.toString() + "\t" + e.getSkillsString()));

	}
}
