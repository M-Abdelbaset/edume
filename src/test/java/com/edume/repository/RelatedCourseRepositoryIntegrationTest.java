package com.edume.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.edume.entity.CourseCategoryEntity;
import com.edume.entity.CourseEntity;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
@Sql(scripts = {"classpath:categories.sql", "classpath:related_courses.sql"})
class RelatedCourseRepositoryIntegrationTest {

	@Autowired RelatedCoursesRepository relatedCoursesRepository;
	@Autowired JdbcTemplate testTemplate;
	
	@Test
	void whenQueryRelatedCourses_thenTopVotes3AreReturned() {
		
		List<CourseEntity> courses = 
				relatedCoursesRepository.findRelatedCourses(1l, PageRequest.of(0, 3));
		
		assertThat(courses.size(), is(3));
		assertThat(courses.get(0), equalTo(new CourseEntity("Introduction to Kubernates")));
		assertThat(courses.get(1), equalTo(new CourseEntity("Spring Boot basics")));
		assertThat(courses.get(2), equalTo(new CourseEntity("Junit")));
	}
	
	@Test
	void whenNewRelatedCourseInSameCategory_ThenSave() {
		
		CourseEntity currentCourse = new CourseEntity("clean code")
				.withCategory(new CourseCategoryEntity("JAV"));
		
	}
	
}
