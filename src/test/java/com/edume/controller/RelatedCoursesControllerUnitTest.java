package com.edume.controller;

import static com.edume.controller.RelatedCoursesController.RELATED_COURSES_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.edume.model.Course;
import com.edume.model.RelatedCourse;
import com.edume.service.CourseService;

@ExtendWith(MockitoExtension.class)
class RelatedCoursesControllerUnitTest {

	MockMvc mockMvc;
	
	@Mock CourseService courseService;
	@InjectMocks RelatedCoursesController relatedCoursesController;
	
	@BeforeEach
	void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(relatedCoursesController).build();
	}
	
	@Test
	void whenNotRelatedCourses_thenReturn400() throws Exception {
		
		when(courseService.addRelatedCourse(any(Course.class), any(Course.class))).thenReturn(null);
		
		mockMvc.perform(post(RELATED_COURSES_PATH)
				.param("courseName", "a").param("courseCat", "x")
				.param("relatedToName", "a").param("relatedToCat", "x"))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	void whenNewRelatedCourse_thenCreated() throws Exception {
		
		RelatedCourse relatedCourse = new RelatedCourse(new Course("test"), 1);
		when(courseService.addRelatedCourse(any(Course.class), any(Course.class))).thenReturn(relatedCourse);
		
		mockMvc.perform(post(RELATED_COURSES_PATH)
				.param("courseName", "a").param("courseCat", "x")
				.param("relatedToName", "a").param("relatedToCat", "x"))
		.andExpect(status().is(201));
	}
	
	@Test
	void whenExistingRelatedCourse_thenUpdated() throws Exception {
		
		RelatedCourse relatedCourse = new RelatedCourse(new Course("test"), 29);
		when(courseService.addRelatedCourse(any(Course.class), any(Course.class))).thenReturn(relatedCourse);
				
		mockMvc.perform(post(RELATED_COURSES_PATH)
				.param("courseName", "a").param("courseCat", "x")
				.param("relatedToName", "a").param("relatedToCat", "x"))
		.andExpect(status().is(204));
	}
}
