package com.jp.testing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jp.testing.exception.ResourceAlreadyExistException;
import com.jp.testing.exception.ResourceNotFoundException;
import com.jp.testing.modal.EmployeeVO;
import com.jp.testing.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepo employeeRepo;

	public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	@Override
	public EmployeeVO saveEmployee(EmployeeVO employeeVO) {
		Optional<EmployeeVO> savEmployeeVO = employeeRepo.findByEmail(employeeVO.getEmail());
		if (savEmployeeVO.isPresent()) {
			throw new ResourceAlreadyExistException(
					"Employee already exist with given email :" + employeeVO.getEmail());
		}
		return employeeRepo.save(employeeVO);
	}

	@Override
	public List<EmployeeVO> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public Optional<EmployeeVO> getEmployeeById(long id) {
		return employeeRepo.findById(id);
	}

	@Override
	public void deleteEmployeeById(long id) {
		employeeRepo.deleteById(id);
	}

	@Override
	public EmployeeVO updateEmployee(long id,EmployeeVO employeeVO) {
		Optional<EmployeeVO> savEmployeeVO = employeeRepo.findById(id);
		if (savEmployeeVO.isEmpty()) {
			throw new ResourceNotFoundException("Employee not found with given id :" + id);
		}
		return employeeRepo.save(employeeVO);
	}

}
