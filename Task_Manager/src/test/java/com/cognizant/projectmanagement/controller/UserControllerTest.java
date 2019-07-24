package com.cognizant.projectmanagement.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.projectmanagement.dao.User;
import com.cognizant.projectmanagement.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;



@RunWith(SpringRunner.class)
@WebMvcTest(value=UserController.class,secure = false)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@Test
	public void testAddNewUser() throws Exception {
		
		User mockUser =new User();
		mockUser.setFirstName("Rakesh");
		mockUser.setLastName("Kuppuswamy");
		mockUser.setEmployeeId("1");
		
		String inputInJson = this.mapToJson(mockUser);
		
		String URI = "/user/add";
		
		Mockito.when(userService.addNewUser(Mockito.any(User.class))).thenReturn(mockUser);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	
	}
	
	@Test
	public void testGetAllUsers() throws Exception{
		
		User mockUser =new User();
		mockUser.setFirstName("Rakesh");
		mockUser.setLastName("Kuppuswamy");
		mockUser.setEmployeeId("1");
		
	
		
		List<User> userList = new ArrayList<User>();
		userList.add(mockUser);
		
		
		Mockito.when(userService.findAll()).thenReturn(userList);
		
		String URI = "/user/all";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(userList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	
	
	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}
