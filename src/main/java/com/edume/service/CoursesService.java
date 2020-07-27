package com.edume.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edume.model.CourseCategoryHolder;
import com.edume.model.CourseCategoryHolder.CourseCategory;

@Service
public class CoursesService {

	public CourseCategoryHolder getCategories() {
		List<CourseCategory> categories = List.of(new CourseCategory("CS01", "programming", null),
				new CourseCategory("EC01", "economics", null));
		
		return new CourseCategoryHolder(categories);
	}
}
