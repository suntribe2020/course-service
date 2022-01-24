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

    private static final Long ID = 1L;

    @Test
    void shouldSaveCourseSuccessfully() {
        Course testCourse = getTestCourse();

        when(courseRepository.save(testCourse)).thenReturn(testCourse);
        assertEquals(testCourse, courseService.save(testCourse));
    }

    @Test
    void shouldFindCourseById(){
        Course testCourse = getTestCourse();

        when(courseRepository.findCourseById(ID)).thenReturn(testCourse);
        Course actualCourse = courseService.findCourseById(ID);

        verify(courseRepository).findCourseById(ID);
        assertEquals(courseRepository.findCourseById(ID), actualCourse);
    }

    @Test
    void shouldGetAllCoursesSuccessfully() {
        List<Course> allTestCourses = new ArrayList<>();
        allTestCourses.add(new Course(1L,"Java","Java for beginners","Three weeks"));
        allTestCourses.add(new Course(2L,"Kotlin","Kotlin for beginners","Two weeks"));

        when(courseRepository.findAll()).thenReturn(allTestCourses);
        assertEquals(2, courseService.findAll().size());
    }

    @Test
    void shouldDeleteCourseById() {
        Course testCourse = getTestCourse();

        when(courseRepository.save(testCourse)).thenReturn(testCourse);

        Course actualCourse = courseService.save(testCourse);
        assertEquals(testCourse.getId(), actualCourse.getId());

        courseService.deleteById(ID);
        verify(courseRepository,times(1)).deleteById(testCourse.getId());
    }

    @Test
    void shouldUpdateCourseSuccessfully() {
        Course testCourse = getTestCourse();

        when(courseRepository.findCourseById(ID)).thenReturn(testCourse);
        when(courseService.findAll()).thenReturn(List.of(testCourse));

        Course updatedCourse = new Course();
        updatedCourse.setId(ID);
        updatedCourse.setCourseId("updatedCourseId");
        updatedCourse.setCourseTitle("UpdatedCourseTitle");
        updatedCourse.setDuration("updatedDuration");

        courseService.updateCourse(updatedCourse);

        List<Course> actual = courseService.findAll();
        Course actualCourse = actual.get(0);

        assertEquals(ID, actualCourse.getId());
        assertEquals(testCourse.getCourseId(), actualCourse.getCourseId());
        assertEquals(testCourse.getCourseTitle(), actualCourse.getCourseTitle());
        assertEquals(testCourse.getDuration(), actualCourse.getDuration());
    }

    private Course getTestCourse() {
        Course course = new Course();
        course.setId(ID);
        course.setCourseId("courseId");
        course.setCourseTitle("courseTitle");
        course.setDuration("duration");

        return course;
    }
}
