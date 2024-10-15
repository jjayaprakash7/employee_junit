package com.jp.testing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jp.testing.modal.EmployeeVO;

@Service
public interface EmployeeService {
	EmployeeVO saveEmployee(EmployeeVO employeeVO);
	
	List<EmployeeVO> getAllEmployee();
	
	Optional<EmployeeVO> getEmployeeById(long id);
	
	void deleteEmployeeById(long id);
	
	EmployeeVO updateEmployee(long id, EmployeeVO employeeVO);
}
