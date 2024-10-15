package com.jp.testing.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jp.testing.modal.EmployeeVO;

@DataJpaTest
class EmployeeRepoTest {

	@Autowired
	private EmployeeRepo employeeRepo;

	private EmployeeVO employeeVO;

	@BeforeEach
	void setup() {
		employeeVO = EmployeeVO.builder().firstName("Jayaprakash").lastName("Jayaraman").email("jjpece7@gmail.com")
				.build();
	}

	@DisplayName("Junit test for save employee")
	@Test
	public void givenEmployeeObjectWhenSaveThenReturnSaveEmployee() {

		EmployeeVO saveEmployeeVO = employeeRepo.save(employeeVO);
		assertThat(saveEmployeeVO).isNotNull();
		assertThat(saveEmployeeVO.getId()).isGreaterThan(0);
	}

	@DisplayName("Junit test for find All employee")
	@Test
	public void givenEmployeeListWhenFindAllEmployeeThenReturnAllEmployee() {
		EmployeeVO employeeVO1 = EmployeeVO.builder().firstName("Second").lastName("Employee").email("test@gmail.com")
				.build();
		employeeRepo.save(employeeVO);
		employeeRepo.save(employeeVO1);

		List<EmployeeVO> AllEmployeeVO = employeeRepo.findAll();

		assertThat(AllEmployeeVO).isNotNull();
		assertThat(AllEmployeeVO.size()).isEqualTo(2);

	}

	@DisplayName("Junit test for find employee by id")
	@Test
	public void givenEmployeeIdWhenFindByIdThenReturnEmployeeById() {

		employeeRepo.save(employeeVO);

		EmployeeVO returnEmployeeVO = employeeRepo.findById(employeeVO.getId()).get();

		assertThat(returnEmployeeVO).isNotNull();

	}

	@DisplayName("Junit test for find employee by email using JPA query")
	@Test
	public void givenEmailWhenFindByEmailThenReturnEmployee() {

		employeeRepo.save(employeeVO);

		EmployeeVO returnEmployeeVO = employeeRepo.findByEmail(employeeVO.getEmail()).get();

		assertThat(returnEmployeeVO).isNotNull();

	}

	@DisplayName("Junit test for update employee operation")
	@Test
	public void givenEmployeeObjectWhenUpdateEmployeeThenReturnUpdatedEmployee() {
		employeeRepo.save(employeeVO);
		EmployeeVO saveEmployeeVO = employeeRepo.findById(employeeVO.getId()).get();

		saveEmployeeVO.setFirstName("JP");
		saveEmployeeVO.setLastName("RJ");
		saveEmployeeVO.setEmail("jjptest@test.com");
		EmployeeVO updatedEmployee = employeeRepo.save(saveEmployeeVO);

		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getFirstName()).isEqualTo("JP");
		assertThat(updatedEmployee.getLastName()).isEqualTo("RJ");
		assertThat(updatedEmployee.getEmail()).isEqualTo("jjptest@test.com");

	}

	@DisplayName("Junit test for delete employee by id")
	@Test
	public void givenEmployeeObjectWhenDeleteThenRemoveEmployee() {
		employeeRepo.save(employeeVO);

		employeeRepo.deleteById(employeeVO.getId());

		Optional<EmployeeVO> employeeOptional = employeeRepo.findById(employeeVO.getId());

		assertThat(employeeOptional).isEmpty();

	}

	@DisplayName("Junit test for find employee by first_name and last_name using JPQL query")
	@Test
	public void givenFirstNameAndLastNameWhenFindByFirstNameAndLastNameThenReturnEmployee() {
		employeeRepo.save(employeeVO);

		EmployeeVO returnEmployeeVO = employeeRepo.findByfirstNameAndSecondName(employeeVO.getFirstName(),
				employeeVO.getLastName());

		assertThat(returnEmployeeVO).isNotNull();

	}

	@DisplayName("Junit test for find employee by first_name and last_name using JPQL query with named Param")
	@Test
	public void givenNamedParamWhenFindByFirstNameAndLastNameThenReturnEmployee() {
		employeeRepo.save(employeeVO);

		EmployeeVO returnEmployeeVO = employeeRepo.findByfirstNameAndSecondNameUsingNamed(employeeVO.getFirstName(),
				employeeVO.getLastName());

		assertThat(returnEmployeeVO).isNotNull();

	}

	@DisplayName("Junit test for find employee by first_name and last_name using native query")
	@Test
	public void givenFirstNameLastNameWhenFindEmployeeWithNativeThenReturnEmployee() {
		employeeRepo.save(employeeVO);

		EmployeeVO returnEmployeeVO = employeeRepo.findByfirstNameAndSecondNameUsingNative(employeeVO.getFirstName(),
				employeeVO.getLastName());

		assertThat(returnEmployeeVO).isNotNull();
	}

	@DisplayName("Junit test for find employee by first_name and last_name using native query with named param")
	@Test
	public void givenFirstNameLastNameWhenFindEmployeeWithNativeNamedThenReturnEmployee() {
		employeeRepo.save(employeeVO);

		EmployeeVO returnEmployeeVO = employeeRepo
				.findByfirstNameAndSecondNameUsingNativeNamedParam(employeeVO.getFirstName(), employeeVO.getLastName());

		assertThat(returnEmployeeVO).isNotNull();
	}
	
	
}
