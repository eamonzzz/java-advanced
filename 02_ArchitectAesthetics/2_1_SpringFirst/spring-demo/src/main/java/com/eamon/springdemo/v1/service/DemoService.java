package com.eamon.springdemo.v1.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:35
 */
public interface DemoService {
    /**
     * demo查询
     *
     * @param name
     * @param id
     * @param request
     * @param response
     * @return
     */
    String query(String name, String id, HttpServletRequest request, HttpServletResponse response);
}
