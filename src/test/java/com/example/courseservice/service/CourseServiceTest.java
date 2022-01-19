package com.example.courseservice.service;

import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Katri Vidén
 * Date: 2022-01-18
 * Time: 16:07
 * Project: course-service
 * Copyright: MIT
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldSaveCourseSuccessfully() {
        Course course = getTestCourse();

        when(courseRepository.save(course)).thenReturn(course);
        assertEquals(course, courseService.save(course));
    }

    @Test
    void shouldGetAllCoursesSuccessfully() {
        List<Course> testCourses = getTestCourses();

        when(courseRepository.findAll()).thenReturn(testCourses);
        assertEquals(2, courseService.findAll().size());
    }

    @Test
    void shouldDeleteCourseById() {
        Course testCourse = getTestCourse();
        courseService.deleteById(testCourse.getId());

        verify(courseRepository,times(1)).deleteById(testCourse.getId());
    }

    private Course getTestCourse() {
        return new Course(1L,"Java","Java for beginners","Three weeks");
    }

    private List<Course> getTestCourses() {
        List<Course> allCourses = new ArrayList<>();
        allCourses.add(new Course(1L,"Java","Java for beginners","Three weeks"));
        allCourses.add(new Course(2L,"Kotlin","Kotlin for beginners","Two weeks"));
        return allCourses;
    }
}
