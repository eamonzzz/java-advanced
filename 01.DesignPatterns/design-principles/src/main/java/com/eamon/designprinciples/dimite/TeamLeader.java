package com.eamon.designprinciples.dimite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午9:17
 */
public class TeamLeader {
    public void checkNumberOfCourses() {
        List<Course> courseList = new ArrayList<Course>();
        for (int i = 0; i < 20; i++) {
            courseList.add(new Course());
        }
        System.out.println("目前已发布的课程数量是:" + courseList.size());
    }
}
