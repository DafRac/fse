package com.cognizant.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.taskmanager.dao.ParentTask;
import com.cognizant.taskmanager.repository.ParentTaskRepository;

@Service
public class ParentTaskService {
	
	@Autowired
	private ParentTaskRepository repo;
	
//	@Autowired
//	private ParentTask ptask;

	public Iterable<ParentTask> getAllUsers() {
		return repo.findAll();
	}
	
	

	public ParentTask addNew(String task) {
		ParentTask ptask = new ParentTask();
		ptask.setParentTask(task);
		return repo.save(ptask);
	}
	

}
