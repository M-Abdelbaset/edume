package com.edume.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.edume.model.CourseCategoryHolder;
import com.edume.model.CourseCategoryHolder.CourseCategory;
import com.edume.service.CourseCategoryService;

@ExtendWith(MockitoExtension.class)
class CoursesControllerUnitTest {

	MockMvc mockMvc;
	
	@Mock CourseCategoryService coursesService;
	@InjectMocks CoursesController coursesController;
	
	@BeforeEach
	void init() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(coursesController)
				.build();
	}
	
	@Test
	void whenGetCourseCategories_thenReturnAllCoursesCategories() throws Exception {
		
		// setup
		CourseCategory springBoot = new CourseCategory("CS03", "spring boot", List.of());
		CourseCategory java = new CourseCategory("CS02", "java", List.of(springBoot));
		CourseCategory programming = new CourseCategory("CS01", "programming", List.of(java));
		CourseCategory economics = new CourseCategory("EC01", "economics", List.of());
		
		List<CourseCategory> categories = List.of(programming, economics);
		CourseCategoryHolder holder = new CourseCategoryHolder(categories);
		when(coursesService.getCategories()).thenReturn(holder);
		String expectedJson = "{\"categories\":[{\"categoryId\":\"CS01\",\"categoryName\":\"programming\",\"subCategories\":[{\"categoryId\":\"CS02\",\"categoryName\":\"java\",\"subCategories\":[{\"categoryId\":\"CS03\",\"categoryName\":\"spring boot\",\"subCategories\":[]}]}]},{\"categoryId\":\"EC01\",\"categoryName\":\"economics\",\"subCategories\":[]}]}";
		
		// test
		mockMvc.perform(get(CoursesController.CATEGORIES_PATH))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json(expectedJson));

	}

	@Test
	void whenNoCategories_thenReturnEmptyJson() throws Exception {
		
		// setup
		CourseCategoryHolder expectedResponse = new CourseCategoryHolder(Collections.emptyList());
		when(coursesService.getCategories()).thenReturn(expectedResponse);
		
		// test
		mockMvc.perform(get(CoursesController.CATEGORIES_PATH))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json("{}"));	
	}

}
