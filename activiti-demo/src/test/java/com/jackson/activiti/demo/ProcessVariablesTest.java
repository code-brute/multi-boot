package com.jackson.activiti.demo;

import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import jdk.internal.util.xml.impl.Input;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-04-08 下午3:07
 */
public class ProcessVariablesTest extends ActivitiDemoApplicationTests{

    Logger logger = LoggerFactory.getLogger(ProcessVariablesTest.class);


    /**
     * 部署流程定义 两种方式 一种zip 流的方式 一种直接读取文件
     * 1、 ACT_RE_DEPLOYMENT 部署对象表
     * 2、ACT_RE_PROCDEF 流程定义表
     * 3、ACT_GE_BYTEARRAY 资源文件表
     * 4、ACT_GE_PROPERTY 主键策略表
     */
    @Test
    public void deploymentProcessDefinition(){

        // ACT_RE_PROCDEF 表中的key 对应的是流程图（bpmn 文件）中的ID  name对应流程图中的name
        // 从classPath 部署
//        Deployment deployment = repositoryService.createDeployment() // 创建一个部署对象
//                //  此处的key  name  category 对应的是ACT_RE_DEPLOYMENT 表中的key name category
//                .key("assetsInPoolProcess")
//                .name("资产入库流程")
//                .category("assets")
//                .addClasspathResource("testprocesses/processVariable.bpmn") // 添加资源
//                .addClasspathResource("testprocesses/processVariable.png")
//                .deploy();// 部署


        // 从zip 流部署
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("testprocesses/process.zip");
//        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
//        repositoryService.createDeployment()
//                .key("assetsInPoolProcess")
//                .name("资产入库流程")
//                .category("assets")
//                .addZipInputStream(zipInputStream)
//                .deploy();

        /**
         *  this.getClass().getResourceAsStream("process.zip");  从当前包下加载指定的文件
         *  this.getClass().getResourceAsStream("/process.zip");  从classpath 根目录加载指定的文件 ⚠️注意有 /
         *  this.getClass().getClassLoader().getResourceAsStream("testprocesses/process.zip") 从classpath 根目录加载指定的文件
         *
         */
        // 从流 inputStream 部署
        InputStream bpmnInputStream = this.getClass().getResourceAsStream("/testprocesses/processVariable.bpmn");
        InputStream pngInputStream = this.getClass().getResourceAsStream("/testprocesses/processVariable.png");
        repositoryService.createDeployment()
                .key("assetsInPoolProcess")
                .name("资产入库流程")
                .category("assets")
                .addInputStream("processVariable.bpmn",bpmnInputStream)
                .addInputStream("processVariable.png",pngInputStream)
                .deploy();

    }

    /**
     * 启动流程
     *
     * act_ru_execution 正在执行的执行对象表
     * act_hi_procinst 流程实力历史表
     * act_ru_task 正在执行的任务表 只有节点是userTask 的时候，该表中存在数据
     * act_hi_taskinst 任务历史表 只有节点是userTask 的时候，该表中有数据
     * act_hi_actinst  所有活动节点的历史表
     */

    @Test
    public void startProcessInstance(){
//        流程ID为 130001   135001
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("assetsInPool");
        log.debug("流程启动成功，流程ID为 {} ", processInstance.getId());
    }


    /**
     * 设置流程变量
     */
    @Test
    public void setVariables(){

//        runtimeService.setVariables(executionId,map); 表示 用执行对象的ID 和 Map 集合设置流程变量，map集合的key 表示 流程变量的name value 表示流程变量的值
//        runtimeService.setVariable(executionId,variablename,value); 表示用执行对象的Id 流程变量name value 设置流程变量，每次只能设置一个
//        runtimeService.setVariableLocal(executionId,variablename,value);
//        runtimeService.setVariablesLocal(executionId,map);

//        runtimeService.startProcessInstanceByKey("", new HashMap<>()); 流程启动的时候设置流程变量


//        taskService.setVariables(taskId,map); 表示用任务id 设置流程变量
//        taskService.setVariable(taskId,variablename,value);
//        taskService.setVariableLocal(taskId,variablename,value);
//        taskService.setVariablesLocal(taskId,map);

//        taskService.complete("", new HashMap<>());  任务完成的时候设置流程变量

//      每次设置一个流程变量，executionId 为流程 执行对象ID，ACT_RU_EXECUTION 表中每个流程至少会生成两条数据
//      如果不是 setVariableLocal 则 ACT_RU_VARIABLE 表中execution_id 显示的是 根execution_id (parentID为null 的那条数据的id)
        runtimeService.setVariable("130002","请假天数",1);
        runtimeService.setVariable("130002","请假原因","回家吃饭");
        runtimeService.setVariable("130002","请假日期",new Date());
        /*** 上面的数据在ACT_RU_VARIABLE 表中显示的 execution_id 为 130002 的根id 130001***/


        runtimeService.setVariableLocal("130002","请假天数",1);
        runtimeService.setVariableLocal("130002","请假原因","回家吃饭");
        runtimeService.setVariableLocal("130002","请假日期",new Date());
        /*** 上面的数据在ACT_RU_VARIABLE 表中显示的 execution_id 为 130002  ，执行对象ID 和 流程变量进行了绑定***/


//        Map<String, Object> map = new HashMap<>();
//        map.put("请假天数", 1);
//        map.put("请假原因", "回家打炮");
//        map.put("请假时间", new Date());
//
//        runtimeService.setVariables("130002", map);
//
//        map.put("请假天数", 2);
//        map.put("请假原因", "回家打炮");
//        map.put("请假时间", new Date());
//
//        runtimeService.setVariablesLocal("130002", map); // 与执行对象id 绑定
////        130005
////        135005
//
//        taskService.setVariables("130005",map);
//        taskService.setVariablesLocal("130005",map); // 与任务id 绑定
////        ACT_RU_VARIABLE; -- 表示正在执行的流程变量 任务完成后 此表就没有数据了，全部转移到 历史表
////
////        ACT_HI_VARINST; -- 表示历史流程变量
//

    }

    /**
     * 获取流程变量
     */
    @Test
    public void getVariables(){

    }

    @Test
    public void removeVariables(){
        List<String> list = new ArrayList<>();
        list.add("请假天数");
        list.add("请假原因");
        list.add("请假时间");

        runtimeService.removeVariable("130001","请假天数");
        runtimeService.removeVariable("130001","请假原因");
        runtimeService.removeVariable("130001","请假日期");
        runtimeService.removeVariable("130001","130001");
        runtimeService.removeVariables("130002",list);
        runtimeService.removeVariablesLocal("130002",list);
        taskService.removeVariables("130005",list);
        taskService.removeVariablesLocal("130005",list);
    }

    /**
     * 查询流程变量历史记录
     */
    @Test
    public void queryHistoryVariable(){

      List<HistoricVariableInstance> historicVariableInstance =
              historyService.createHistoricVariableInstanceQuery().list();

      log.debug("查询历史 流程变量表： {} ", historicVariableInstance);
    }

    /**
     * 完成任务
     */
    @Test
    public void complete(){
        String taskId = "";
        taskService.complete(taskId);
    }
}
