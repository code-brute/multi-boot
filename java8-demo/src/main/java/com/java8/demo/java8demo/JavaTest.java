package com.java8.demo.java8demo;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-01-29 下午4:30
 */
public class JavaTest {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "资产名称");
        jsonObject.put("code", "资产编码");
        jsonObject.put("buyDept", "采购部门");
//        jsonObject.put("ownDept", "归属部门");
//        jsonObject.put("useDept", "使用部门");
//        jsonObject.put("principal", "责任人");
//        jsonObject.put("buyDate", "购入时间");
//        jsonObject.put("receptDate", "领用时间");
//        jsonObject.put("startDate", "开始使用时间");
//        jsonObject.put("serviceLife", "使用年限(月)");
//        jsonObject.put("repoCode", "仓库编码");
//        jsonObject.put("address", "存放地点");
        System.out.println(jsonObject);

    }
}
