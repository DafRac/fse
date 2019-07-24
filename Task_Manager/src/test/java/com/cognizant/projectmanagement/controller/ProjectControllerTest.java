package com.cognizant.projectmanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
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


import com.cognizant.projectmanagement.model.ProjectObj;
import com.cognizant.projectmanagement.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(value=ProjectController.class,secure = false)
public class ProjectControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjectService projectService;
	
	final Date date = Mockito.mock(Date.class);
	
	@Test
	public void testAddProject() throws Exception {
		
		ProjectObj mockProject = new ProjectObj();
		
		mockProject.setProjectName("UI");
		mockProject.setStartDate(date);
		mockProject.setEndDate(date);
		mockProject.setPriority(10);
		mockProject.setUserId(01);
		
		
		String inputInJson = this.mapToJson(mockProject);
		
		String URI = "/project";
		
		Mockito.when(projectService.addProject(Mockito.any(ProjectObj.class))).thenReturn(mockProject);
		
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
	public void testGetAllProject() throws Exception{
		
		ProjectObj mockProject = new ProjectObj();
		
		mockProject.setProjectName("UI");
		mockProject.setStartDate(date);
		mockProject.setEndDate(date);
		mockProject.setPriority(10);
		mockProject.setUserId(01);
		
	
		
		List<ProjectObj> projectList = new ArrayList<ProjectObj>();
		projectList.add(mockProject);
		
		
		Mockito.when(projectService.getAllProject()).thenReturn(projectList);
		
		String URI = "/project/all";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(projectList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	
	
	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	

}
