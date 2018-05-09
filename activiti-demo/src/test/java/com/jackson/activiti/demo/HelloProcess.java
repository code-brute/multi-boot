package com.jackson.activiti.demo;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-04-03 下午3:17
 */
public class HelloProcess extends ActivitiDemoApplicationTests{


    /**
     * 部署流程
     */
    @Test
    public void deployProcesses() {
        Deployment deployment = repositoryService
                .createDeployment() // 创建一个部署对象
                .addClasspathResource("testprocesses/hello.bpmn") // 添加资源
                .deploy(); // 部署

        log.debug("部署流程Id {}", deployment.getId());
        log.debug("部署流程name {}, {}, {} ,{}, {}", deployment.getName(), deployment.getKey(), deployment.getDeploymentTime() , deployment.getTenantId());
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess(){

        // 使用key 来启动流程，该key 对应了 bpmn 中的id ，对应的是 act_re_procdef （业务流程定义数据表）中的key
        // 用key 启动的好处是，一个key 对应多条数据的时候 启动的是最新的额流程
        ProcessInstance processInstance  = runtimeService.startProcessInstanceByKey("请假流程");
        log.debug("流程实力ID {} 流程定义ID {} ", processInstance.getId(), processInstance.getProcessDefinitionId());

    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findPersonTask(){
        List<Task> taskList = taskService
                .createTaskQuery() // 创建任务查询对象
                .taskAssignee("王五") // 指定查询办理人
                .list();
        log.debug("----------------{}", taskList);

        if (Objects.nonNull(taskList) && !taskList.isEmpty()) {
            for (Task task : taskList) {
                log.debug("任务ID：{} ,任务名称：{}，任务的创建时间：{} ，任务的办理人：{} ,流程实例ID：{}, 执行对象ID： {} ,流程定义ID:{} ",
                        task.getId(),task.getName(),task.getCreateTime(),task.getAssignee(),task.getProcessInstanceId(),task.getExecutionId(),task.getProcessDefinitionId());
            }
        }else {
            log.debug("当前员工不存在任务");
        }

    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeTask(){
        taskService.complete("57507");
        log.debug("完成任务，任务ID：{}", 57507);

    }
}
