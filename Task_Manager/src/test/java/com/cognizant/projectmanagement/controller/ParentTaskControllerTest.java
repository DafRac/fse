package com.cognizant.projectmanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.projectmanagement.dao.ParentTask;

import com.cognizant.projectmanagement.service.ParentTaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ParentTaskController.class,secure = false)
public class ParentTaskControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ParentTaskService parentService;
	
	@Test
	public void testGetAllUsers() throws Exception{
		
		ParentTask mockParent=new ParentTask();
		mockParent.setParentTask("First");
		List<ParentTask> userList = new ArrayList<ParentTask>();
		userList.add(mockParent);
		
		
		Mockito.when(parentService.getAllUsers()).thenReturn(userList);
		
		String URI = "/parenttask/all";
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
