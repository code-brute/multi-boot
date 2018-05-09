package com.jackson.activiti.demo;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-04-08 下午2:07
 */
public class ProcessInstanceTest extends ActivitiDemoApplicationTests {


    Logger log = LoggerFactory.getLogger(ProcessInstanceTest.class);

    @Autowired
    HistoryService historyService;

    /**
     * 启动流程
     */
    @Test
    public void startProcessInstance() {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("请假流程");

        log.debug("启动流程实力id {} ", processInstance.getId());
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findPersonTask() {
        List<Task> taskList = taskService
                .createTaskQuery() // 创建任务查询对象
//                .taskAssignee("王五") // 指定查询办理人
                .list();
        log.debug("----------------{}", taskList);

        if (Objects.nonNull(taskList) && !taskList.isEmpty()) {
            for (Task task : taskList) {
                log.debug("任务ID：{} ,任务名称：{}，任务的创建时间：{} ，任务的办理人：{} ,流程实例ID：{}, 执行对象ID： {} ,流程定义ID:{} ",
                        task.getId(), task.getName(), task.getCreateTime(), task.getAssignee(), task.getProcessInstanceId(), task.getExecutionId(), task.getProcessDefinitionId());
            }
        } else {
            log.debug("当前员工不存在任务");
        }

    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeTask() {
        taskService.complete("120002");
        log.debug("完成任务，任务ID：{}", 120002);

    }
    /**
     * act_ru_execution 正在执行的执行对象表
     * act_hi_procinst 流程实力历史表
     * act_ru_task 正在执行的任务表 只有节点是userTask 的时候，该表中存在数据
     * act_hi_taskinst 任务历史表 只有节点是userTask 的时候，该表中有数据
     * act_hi_actinst  所有活动节点的历史表
     */
    /**
     * 查询流程状态
     */
    @Test
    public void isProcessEnd() {

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId("115001")
                .singleResult();
        if (Objects.isNull(processInstance)) {
            log.debug("流程已经结束");
        } else {
            log.debug("流程没有结束");
        }
    }

    /**
     * 查询历史任务
     */

    @Test
    public void queryHistoryTask(){
       List<HistoricTaskInstance> historicTaskInstanceQueries = historyService.createHistoricTaskInstanceQuery().list();

       log.debug("======={}",historicTaskInstanceQueries);
    }

    /**
     * 查询历史流程实力
     */
    @Test
    public void queryHistoryProcessInstance(){
        HistoricProcessInstance historicProcessInstances =
                historyService.createHistoricProcessInstanceQuery().processInstanceId("115001").singleResult();
        log.debug("====={},{}", historicProcessInstances.getName(),historicProcessInstances.getSuperProcessInstanceId());

    }
}
