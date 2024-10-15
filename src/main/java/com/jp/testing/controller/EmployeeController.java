package com.jp.testing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jp.testing.modal.EmployeeVO;
import com.jp.testing.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<EmployeeVO> getAllEmployee() {
		return employeeService.getAllEmployee();
	}

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public EmployeeVO getEmployeeById(@PathVariable long id) {
		return employeeService.getEmployeeById(id).get();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeVO saveEmployee(@RequestBody EmployeeVO employeeVO) {
		return employeeService.saveEmployee(employeeVO);
	}

	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public EmployeeVO saveEmployee(@PathVariable long id, @RequestBody EmployeeVO employeeVO) {
		return employeeService.updateEmployee(id, employeeVO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void saveEmployee(@PathVariable long id) {
		employeeService.deleteEmployeeById(id);
	}
}
