package com.jackson.activiti.demo;


import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipInputStream;

/**
 * @Description: 流程定义是不能修改的
 * @author: mitnick
 * @date: 2018-03-28 下午2:33
 */
public class ProcessDefinitionTest extends ActivitiDemoApplicationTests{

    /**
     * 部署流程涉及四张表
     * 1、 ACT_RE_DEPLOYMENT 部署对象表
     * 2、ACT_RE_PROCDEF 流程定义表
     * 3、ACT_GE_BYTEARRAY 资源文件表
     * 4、ACT_GE_PROPERTY 主键策略表
     */
    /**
     * 部署流程定义从 classpath
     */
    @Test
    public void deployProcesses_classpath() {
        Deployment deployment = repositoryService
                .createDeployment() // 创建一个部署对象
                .addClasspathResource("testprocesses/hello.bpmn") // 添加资源
                .deploy(); // 部署

        log.debug("部署流程Id {}", deployment.getId());
        log.debug("部署流程name {}, {}, {} ,{}, {}", deployment.getName(), deployment.getKey(), deployment.getDeploymentTime() , deployment.getTenantId());
    }

    /**
     * 部署流程定义从 zip
     */
    @Test
    public void deployProcesses_zip() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("testprocesses/hello.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = repositoryService
                .createDeployment() // 创建一个部署对象
                .name("从zip 部署流程定义")
                .key("请假流程")
                .category("假期")
                .addZipInputStream(zipInputStream)
                .deploy(); // 部署

        log.debug("部署流程Id {}", deployment.getId());
        log.debug("部署流程name {}, {}, {} ,{}, {}", deployment.getName(), deployment.getKey(), deployment.getDeploymentTime() , deployment.getTenantId());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessesDefinition(){
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("请假流程:1:40009") // 添加查询条件
                .processDefinitionName("请假流程")
                .processDefinitionKey("请假流程")
                .orderByProcessDefinitionVersion().asc() // 按照版本的升序排列
//                .listPage() 分页查询
//                .singleResult() 返回唯一结果集
//                .count() 返回数量
                .list();


        log.debug("流程定义查询结果：{} ", processDefinitions);

        for (ProcessDefinition processDefinition : processDefinitions) {
            log.debug("id:{}, name:{}, key:{}, version:{}, resourceName:{}, category:{} ,description:{}  ",
                    processDefinition.getId(),processDefinition.getName(),processDefinition.getKey(),processDefinition.getVersion(),
                    processDefinition.getResourceName(),processDefinition.getCategory(),processDefinition.getDescription());
            log.debug("============================================");
        }
    }

    /**
     * 删除流程
     */
    @Test
    public void deleteProcess(){
        // 普通删除 如果当前规则下有 正在执行的流程，则抛出异常

        String deploymentId = "100001";
        try {
            log.info("普通删除 如果当前规则下有 正在执行的流程，则抛出异常");
            repositoryService.deleteDeployment(deploymentId);
        } catch (Exception e) {
            log.info("普通删除失败，需要进行级联删除",e.getMessage());
            log.info("级联删除会删除该流程相关的所有信息，包含正在执行的信息，历史信息等");
            repositoryService.deleteDeployment(deploymentId, true);
        }
    }

    /**
     * 获取流程自定义文档资源  查看流程图
     */
    @Test
    public void viewImage() throws IOException {

        String deploymentId = "70006";
        List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
        log.debug("=============={}", names);

        String imageName = null;
        if (Objects.nonNull(names) && !names.isEmpty()) {
            for (String name : names) {
                if (name.indexOf(".png") > 0) {
                    imageName = name;
                }
            }
        }
        log.debug("========={} ",imageName);
        if (StringUtils.isNotBlank(imageName)) {
            File file = new File("/Users/mitnick/code/imageName");
            InputStream inputStream =  repositoryService.getResourceAsStream(deploymentId, imageName);
            FileUtils.copyInputStreamToFile(inputStream,file);
        }
    }

    /**
     *
     */
    @Test
    public void lastVersionProcessDefinition(){

    }
}
