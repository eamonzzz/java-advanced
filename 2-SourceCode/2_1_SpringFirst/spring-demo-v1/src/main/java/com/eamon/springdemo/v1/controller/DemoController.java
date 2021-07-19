package com.eamon.springdemo.v1.controller;

import com.eamon.springdemo.annontation.MyAutowired;
import com.eamon.springdemo.annontation.MyController;
import com.eamon.springdemo.annontation.MyRequestMapping;
import com.eamon.springdemo.annontation.MyRequestParam;
import com.eamon.springdemo.v1.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:35
 */
@MyController
@MyRequestMapping("/api/v1/demo")
public class DemoController {
    @MyAutowired
    DemoService demoService;

    @MyRequestMapping("/query")
    public String query(@MyRequestParam("name") String name, @MyRequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
        return demoService.query(name, id, request, response);
    }

}
