package com.jp.testing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jp.testing.exception.ResourceAlreadyExistException;
import com.jp.testing.exception.ResourceNotFoundException;
import com.jp.testing.modal.EmployeeVO;
import com.jp.testing.repo.EmployeeRepo;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepo employeeRepo;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private EmployeeVO employeeVO;

	@BeforeEach
	public void setup() {
//		employeeRepo = Mockito.mock(EmployeeRepo.class);
//		employeeService = new EmployeeServiceImpl(employeeRepo);
		employeeVO = EmployeeVO.builder().id(1L).email("jjpece7@gmail.com").firstName("Jayaprakash")
				.lastName("Jayaraman").build();
	}

	@DisplayName("JUnit test for save employee")
	@Test
	public void givenEmployeeObjectWhenSaveThenReturnEmployeeObject() {

		given(employeeRepo.findByEmail(employeeVO.getEmail())).willReturn(Optional.empty());
		given(employeeRepo.save(employeeVO)).willReturn(employeeVO);

		EmployeeVO savedEmployee = employeeService.saveEmployee(employeeVO);

		assertThat(savedEmployee).isNotNull();
	}

	@DisplayName("JUnit test for save employee which throws exception")
	@Test
	public void givenEmployeeObjectWhenSaveThenReturnException() {

		given(employeeRepo.findByEmail(employeeVO.getEmail())).willReturn(Optional.of(employeeVO));

		Assertions.assertThrows(ResourceAlreadyExistException.class, () -> employeeService.saveEmployee(employeeVO));

		verify(employeeRepo, never()).save(any(EmployeeVO.class));
	}

	@DisplayName("JUnit test for get all employee")
	@Test
	public void givenEmployeeListWhenGetAllEmployeesThenReturnAllEmployee() {

		EmployeeVO employeeVO1 = EmployeeVO.builder().id(1L).email("jjTest7@gmail.com").firstName("Firstname")
				.lastName("Lastname").build();

		given(employeeRepo.findAll()).willReturn(List.of(employeeVO, employeeVO1));

		List<EmployeeVO> allEmployees = employeeService.getAllEmployee();

		assertThat(allEmployees).isNotNull();
		assertThat(allEmployees.size()).isEqualTo(2);

	}

	@DisplayName("JUnit test for get all employee with negative scenario")
	@Test
	public void givenEmployeeListWhenGetAllEmployeesThenReturnEmptyEmployeeList() {

		given(employeeRepo.findAll()).willReturn(Collections.emptyList());

		List<EmployeeVO> allEmployees = employeeService.getAllEmployee();

		assertThat(allEmployees).isEmpty();
		assertThat(allEmployees.size()).isEqualTo(0);

	}

	@DisplayName("JUnit test for get employee by id")
	@Test
	public void givenIdWhenGetEmployeesByIdThenReturnEmployee() {

		given(employeeRepo.findById(employeeVO.getId())).willReturn(Optional.of(employeeVO));

		EmployeeVO savedEmployees = employeeService.getEmployeeById(employeeVO.getId()).get();

		assertThat(savedEmployees).isNotNull();
	}

	@DisplayName("JUnit test for update employee")
	@Test
	public void givenEmployeeObjectWhenUpdateEmployeesThenReturnEmployee() {

		EmployeeVO updateEmployeeVO = EmployeeVO.builder().id(1L).email("jjpece7@update.com").firstName("JP")
				.lastName("RJ").build();
		given(employeeRepo.findById(employeeVO.getId())).willReturn(Optional.of(employeeVO));

		given(employeeRepo.save(updateEmployeeVO)).willReturn(updateEmployeeVO);

		EmployeeVO updatedEmployeeVO = employeeService.updateEmployee(1L,updateEmployeeVO);

		assertThat(updatedEmployeeVO).isNotNull();
		assertThat(updatedEmployeeVO.getEmail()).isEqualTo("jjpece7@update.com");
		assertThat(updatedEmployeeVO.getFirstName()).isEqualTo("JP");
		assertThat(updatedEmployeeVO.getLastName()).isEqualTo("RJ");
		assertThat(updatedEmployeeVO.getId()).isEqualTo(1L);

	}

	@DisplayName("JUnit test for update employee throw exception")
	@Test
	public void givenEmployeeObjectWhenUpdateEmployeesThenThrowException() {

		EmployeeVO updateEmployeeVO = EmployeeVO.builder().id(1L).email("jjpece7@update.com").firstName("JP")
				.lastName("RJ").build();

		given(employeeRepo.findById(employeeVO.getId())).willReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class,
				() -> employeeService.updateEmployee(1L,updateEmployeeVO));

		verify(employeeRepo, never()).save(any(EmployeeVO.class));
	}

	@DisplayName("JUnit test for delete employee by id")
	@Test
	public void givenIdWhenDeleteEmployeesByIdThenRemoveEmployee() {
		BDDMockito.willDoNothing().given(employeeRepo).deleteById(employeeVO.getId());

		employeeService.deleteEmployeeById(employeeVO.getId());

		verify(employeeRepo,times(1)).deleteById(1L);;
	}
}
