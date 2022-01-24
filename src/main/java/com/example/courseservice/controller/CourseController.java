package com.example.courseservice.controller;

import com.example.courseservice.entity.Course;
import com.example.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Katri Vidén
 * Date: 2021-12-21
 * Time: 21:30
 * Project: course-service
 * Copyright: MIT
 */

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/save")
    public Course saveCourse(@RequestBody Course course) {
        return courseService.save(course);
    }

    @PatchMapping("/updateCourse")
    public Course updateCourse(@RequestBody Course course) {
        return courseService.updateCourse(course);
    }

    @GetMapping("/findById/{id}")
    public Course findCourseById(@PathVariable("id") Long id) {
        return courseService.findCourseById(id);
    }

    @GetMapping("/findAll")
    public List<Course> findAll() {
        return courseService.findAll();
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return courseService.deleteById(id);
    }
}
