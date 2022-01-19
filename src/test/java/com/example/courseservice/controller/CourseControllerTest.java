package com.example.courseservice.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Katri Vidén
 * Date: 2022-01-18
 * Time: 16:23
 * Project: course-service
 * Copyright: MIT
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CourseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String TEST_ID = "" + System.currentTimeMillis();
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_TITLE = "courseTitle";
    private static final String DURATION = "duration";

    @Test
    void shouldBeAbleToSaveCourse() throws JSONException {
        //Create a course
        JSONObject body = generateCourseBody();
        createNewCourse(body);

        //Find and get the created course
        String allCoursesAsJsonArray = getAllCourses();
        JSONObject testCourse = getCreatedTestCourse(allCoursesAsJsonArray);

        assertNotNull(testCourse);
    }

    private void createNewCourse(JSONObject body) {
        webTestClient.post()
                .uri("/course/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body.toString())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    private String getAllCourses() {
        return webTestClient.get()
                .uri("/course/findAll")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody()
                .blockFirst();
    }

    private void deleteCourse(String id) {
        webTestClient.delete()
                .uri("/course/deleteById?id=" + id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    private JSONObject generateCourseBody() throws JSONException {
        JSONObject body = new JSONObject();
        body.put(COURSE_ID, COURSE_ID + TEST_ID);
        body.put(COURSE_TITLE, COURSE_TITLE + TEST_ID);
        body.put(DURATION, DURATION + TEST_ID);

        return body;
    }

    private JSONObject getCreatedTestCourse(String allCoursesAsJsonArray) throws JSONException {
        JSONArray allCourses = new JSONArray(allCoursesAsJsonArray);
        for (int i = 0; i < allCourses.length() ; i++) {
            JSONObject course = allCourses.getJSONObject(i);
            if (course.getString(COURSE_TITLE).endsWith(TEST_ID)) {
                return course;
            }
        }
        return null;
    }
}

