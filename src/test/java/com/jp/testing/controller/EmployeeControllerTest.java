package com.jp.testing.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.testing.modal.EmployeeVO;
import com.jp.testing.service.EmployeeService;

import net.bytebuddy.implementation.bind.annotation.Argument;

@WebMvcTest
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenEmployeeeObjectWhenCreateEmployeeThenReturnSavedEmployee()
			throws JsonProcessingException, Exception {

		EmployeeVO employeeVO = EmployeeVO.builder().firstName("Jayaprakash").lastName("Jayaraman")
				.email("jjpece7@gmail.com").build();
		BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeVO.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));

		ResultActions response = mockMvc.perform(post("/api/employee").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employeeVO)));

		response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employeeVO.getFirstName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employeeVO.getLastName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employeeVO.getEmail())));

	}

}
