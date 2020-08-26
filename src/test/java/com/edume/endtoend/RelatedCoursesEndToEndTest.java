package com.edume.endtoend;

import static com.edume.controller.RelatedCoursesController.RELATED_COURSES_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Sql(scripts = {"classpath:categories.sql", "classpath:related_courses.sql"})
class RelatedCoursesEndToEndTest {

	@Autowired MockMvc mockMvc;
	
	@Test
	void whenNotRelatedCourses_thenReturn400() throws Exception {
		
		mockMvc.perform(post(RELATED_COURSES_PATH)
				.param("courseName", "Spring Boot basics").param("courseCat", "JAV")
				.param("relatedToName", "Python for dummies!").param("relatedToCat", "PYT"))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	void whenNewRelatedCourse_thenCreated() throws Exception {
		
		mockMvc.perform(post(RELATED_COURSES_PATH)
				.param("courseName", "Spring Boot basics").param("courseCat", "JAV")
				.param("relatedToName", "Junit").param("relatedToCat", "JAV"))
		.andExpect(status().is(201));
	}
	
	@Test
	void whenExistingRelatedCourse_thenUpdated() throws Exception {
		
		mockMvc.perform(post(RELATED_COURSES_PATH)
				.param("courseName", "learn Java in 10 day!").param("courseCat", "JAV")
				.param("relatedToName", "Introduction to Docker").param("relatedToCat", "JAV"))
		.andExpect(status().is(204));
	}
}
