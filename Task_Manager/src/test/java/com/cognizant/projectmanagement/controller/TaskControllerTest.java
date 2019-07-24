package com.cognizant.projectmanagement.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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


import com.cognizant.projectmanagement.model.TaskObj;
import com.cognizant.projectmanagement.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(value=TaskController.class,secure = false)
public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService taskService;
	
	final Date date = Mockito.mock(Date.class);
	
	@Test
	public void testAddNewTask() throws Exception {
		
		TaskObj mockTask = new TaskObj();
		mockTask.setParentTaskId(1);
		mockTask.setProjectId(1);
		mockTask.setTaskName("A");
		mockTask.setStartDate(date);
		mockTask.setEndDate(date);
		mockTask.setPriority(1);
		mockTask.setUserId(1);	
		
		String inputInJson = this.mapToJson(mockTask);
		
		String URI = "/task/add";
		
		Mockito.when(taskService.addNewTask(Mockito.any(TaskObj.class))).thenReturn("Saved");
		
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
	public void testGetTasksByProject() throws Exception {
		TaskObj mockTask = new TaskObj();
		mockTask.setParentTaskId(1);
		mockTask.setProjectId(1);
		mockTask.setTaskName("A");
		mockTask.setStartDate(date);
		mockTask.setEndDate(date);
		mockTask.setPriority(1);
		mockTask.setUserId(1);
		
		Mockito.when(taskService.getTasksByProject(Mockito.anyInt())).thenReturn((Iterable<TaskObj>) mockTask);
		
		String URI = "/task/project/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockTask);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	public void testGetAllTasks() throws Exception {

		TaskObj mockTask = new TaskObj();
		mockTask.setParentTaskId(1);
		mockTask.setProjectId(1);
		mockTask.setTaskName("A");
		mockTask.setStartDate(date);
		mockTask.setEndDate(date);
		mockTask.setPriority(1);
		mockTask.setUserId(1);
		
		List<TaskObj> ticketList = new ArrayList<TaskObj>();
		ticketList.add(mockTask);
		
		
		Mockito.when(taskService.getAllTasks()).thenReturn(ticketList);
		
		String URI = "/task/all";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(ticketList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	
	
	
	
	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
