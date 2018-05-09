package com.jackson.activiti.demo;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDemoApplicationTests {

	Logger log = LoggerFactory.getLogger(ActivitiDemoApplicationTests.class);

	// ProcessEngine 流程引擎
	// 与流程定义和流程部署相关的service
	@Autowired
	RepositoryService repositoryService;

	//与正在执行的和执行的流程对象相关的service
	@Autowired
	RuntimeService runtimeService;

	// 与正在执行的任务管理相关的service
	@Autowired
	TaskService taskService;

	// 查询历史表
	@Autowired
	HistoryService historyService;

}
