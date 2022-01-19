package com.example.courseservice.repository;

import com.example.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Katri Vidén
 * Date: 2021-12-21
 * Time: 21:28
 * Project: course-service
 * Copyright: MIT
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course getCourseById(Long id);

    @Transactional
    void deleteById(Long id);
}
