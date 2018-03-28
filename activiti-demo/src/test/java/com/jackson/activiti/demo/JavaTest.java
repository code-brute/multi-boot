package com.jackson.activiti.demo;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-02-06 下午4:33
 */
public class JavaTest {

    public static void main(String[] args) {
       Deque deque = new LinkedList<Serializable>();
       deque.push("11111");
       deque.push("22222");

        System.out.println(deque.contains("11111"));
    }
}
