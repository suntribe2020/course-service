package com.example.courseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Katri Vidén
 * Date: 2021-12-21
 * Time: 21:25
 * Project: course-service
 * Copyright: MIT
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String courseId;
    private String courseTitle;
    private String duration;

    public boolean isCourseId() {
        return courseId != null;
    }

    public boolean isCourseTitle() {
        return courseTitle != null;
    }

    public boolean isDuration() {
        return duration != null;
    }
}
