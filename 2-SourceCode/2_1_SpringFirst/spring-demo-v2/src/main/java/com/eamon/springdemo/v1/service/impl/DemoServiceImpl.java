package com.eamon.springdemo.v1.service.impl;

import com.eamon.springdemo.annontation.MyService;
import com.eamon.springdemo.v1.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:35
 */
@MyService
public class DemoServiceImpl implements DemoService {

    @Override
    public String query(String name, String id, HttpServletRequest request, HttpServletResponse response) {
        return "My name is " + name + " , and id = " + id;
    }
}
