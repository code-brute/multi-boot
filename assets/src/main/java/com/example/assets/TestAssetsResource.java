package com.example.assets;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @author: mitnick
 * @date: 2017-11-06 下午5:35
 */
@RestController
@RequestMapping("/api/assets")
public class TestAssetsResource {

    @PostConstruct
    public void init() {
        System.out.println("assets ++++++++++++");
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        return "===";
    }
}
