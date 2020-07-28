package com.edume.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edume.model.CourseCategoryHolder;

class CoursesServiceUnitTest {

	@Autowired CoursesService coursesService;
	
	@Test
	void whenGetCategories_thenReturnAll() {
		CourseCategoryHolder categories = coursesService.getCategories();
		
	}
}
