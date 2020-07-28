package com.edume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edume.entity.CourseCategory;

@Repository
public interface CoursesRepository extends JpaRepository<CourseCategory, Integer> {
	
	List<CourseCategory> findByParentCategoryIsNull();
}
