package com.edume.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edume.model.CourseCategoryHolder;
import com.edume.model.CourseCategoryHolder.CourseCategory;
import com.edume.repository.CoursesRepository;

@Service
public class CoursesService {

	@Autowired CoursesRepository coursesRepo;
	
	public CourseCategoryHolder getCategories() {
		List<CourseCategory> categories = List.of(new CourseCategory("CS01", "programming", null),
				new CourseCategory("EC01", "economics", null));
		
		return new CourseCategoryHolder(categories);
	}
}
