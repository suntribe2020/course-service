package com.example.courseservice.service;

import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katri Vidén
 * Date: 2021-12-21
 * Time: 21:29
 * Project: course-service
 * Copyright: MIT
 */

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.getCourseById(id);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public String deleteById(Long id) {
        courseRepository.deleteById(id);
        return String.format("Course with id:%s is now deleted", id);
    }

    public Course updateCourse(Course course) {
        Course updateCourse = courseRepository.getCourseById(course.getId());
        updateCourse.setCourseId(course.getCourseId());
        updateCourse.setCourseTitle(course.getCourseTitle());
        updateCourse.setDuration(course.getDuration());
        courseRepository.save(updateCourse);

        return updateCourse;
    }
}
