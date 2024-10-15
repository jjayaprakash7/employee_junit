package com.jp.testing.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jp.testing.modal.EmployeeVO;

public interface EmployeeRepo extends JpaRepository<EmployeeVO, Long> {

	Optional<EmployeeVO> findByEmail(String email);

	@Query("select e from EmployeeVO e where e.firstName=?1 and e.lastName=?2")
	EmployeeVO findByfirstNameAndSecondName(String firstName, String secondName);

	@Query("select e from EmployeeVO e where e.firstName =:firstName and e.lastName =:secondName")
	EmployeeVO findByfirstNameAndSecondNameUsingNamed(@Param("firstName") String firstName,
			@Param("secondName") String secondName);

	@Query(value = "select * from employees  where first_name=?1 and last_name=?2", nativeQuery = true)
	EmployeeVO findByfirstNameAndSecondNameUsingNative(String firstName, String secondName);

	@Query(value = "select * from employees  where first_name=:firstName and last_name=:lastName", nativeQuery = true)
	EmployeeVO findByfirstNameAndSecondNameUsingNativeNamedParam(@Param("firstName") String firstName,
			@Param("lastName") String secondName);
}
