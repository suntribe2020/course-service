package com.example.courseservice.controller;

import com.example.courseservice.entity.Course;
import com.example.courseservice.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Katri Vidén
 * Date: 2022-01-18
 * Time: 16:23
 * Project: course-service
 * Copyright: MIT
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    MockMvc mockMvc;

    //For serialization purposes
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Long ID = 1L;
    private static final String COURSE_TITLE = "Java";
    private static final String COURSE_ID = "JAVA-20";
    private static final String DURATION = "Five weeks";

    @Test
    void saveCourseTest() throws Exception {
        Course testCourse = getTestCourse();

        when(courseService.save(any(Course.class))).thenReturn(testCourse);

        mockMvc.perform(post("/course/save")
                        .content(objectMapper.writeValueAsString(testCourse))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        //check the returned data
                        .andExpect(jsonPath("$.id").value(testCourse.getId()))
                        .andExpect(jsonPath("$.courseTitle").value(testCourse.getCourseTitle()))
                        .andExpect(jsonPath("$.courseId").value(testCourse.getCourseId()))
                        .andExpect(jsonPath("$.duration").value(testCourse.getDuration()));
    }

    @Test
    void updateCourseTest() throws Exception {
        Course testCourse = getTestCourse();

        testCourse.setCourseTitle("Object oriented programming");
        testCourse.setCourseId("OOP-20");
        testCourse.setDuration("Four weeks");

        when(courseService.updateCourse(any(Course.class))).thenReturn(testCourse);

        mockMvc.perform(patch("/course/updateCourse")
                .content(objectMapper.writeValueAsString(testCourse))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //check the returned data
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.courseTitle").value("Object oriented programming"))
                .andExpect(jsonPath("$.courseId").value("OOP-20"))
                .andExpect(jsonPath("$.duration").value("Four weeks"));
    }

    @Test
    void getAllCoursesTest() throws Exception {
        Course testCourse = getTestCourse();
        List<Course> allCourses = List.of(testCourse);

        when(courseService.findAll()).thenReturn(allCourses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/course/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getCourseByIdTest() throws Exception {
        Course testCourse = getTestCourse();

        when(courseService.findCourseById(testCourse.getId())).thenReturn(testCourse);

        mockMvc.perform(get("/course/findById/" + testCourse.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(testCourse.getId()))
                        .andExpect(jsonPath("$.courseTitle").value(testCourse.getCourseTitle()))
                        .andExpect(jsonPath("$.courseId").value(testCourse.getCourseId()))
                        .andExpect(jsonPath("$.duration").value(testCourse.getDuration()));
    }

    @Test
    void deleteCourseByIdTest() throws Exception {
        Course testCourse = getTestCourse();

        when(courseService.deleteById(testCourse.getId())).thenReturn(String.valueOf(testCourse));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/course/deleteById/" + testCourse.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    private Course getTestCourse() {
        Course testCourse = new Course();
        testCourse.setId(ID);
        testCourse.setCourseTitle(COURSE_TITLE);
        testCourse.setCourseId(COURSE_ID);
        testCourse.setDuration(DURATION);

        return testCourse;
    }
}

