package com.jackson.activiti.demo.config;

import com.jackson.activiti.demo.ActivitiDemoApplication;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-02-05 下午4:54
 */
@Configuration
@EnableAutoConfiguration
public class ActivitiConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ActivitiConfiguration.class);

//    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                log.debug("Number of process definitions : "
                        + repositoryService.createProcessDefinitionQuery().count());
                log.debug("Number of tasks : " + taskService.createTaskQuery().count());
                runtimeService.startProcessInstanceByKey("oneTaskProcess");
                log.debug("Number of tasks after process start: " + taskService.createTaskQuery().count());
            }
        };

    }
}

