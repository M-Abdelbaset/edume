package com.edume.endtoend;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.edume.model.Course;
import com.edume.model.RelatedCourse;
import com.edume.service.CourseService;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = {"classpath:categories.sql", "classpath:related_courses.sql"})
public class CourseServiceEndToEnd {

	@Autowired CourseService courseService;
	@Autowired JdbcTemplate jdbcTemplate;
	@Autowired EntityManager em;
	
	@Test
	void whenGetRelatedCourses_thenReturnTop3() {
		
		List<Course> actualCourses = courseService.getRelatedCourses(1l);
		
		assertThat(actualCourses.size(), is(3));
		
		Course kubernates = new Course("Introduction to Kubernates")
				.withCategory("JAV").withDescription("Introduction to Kubernates")
				.withPrice(new BigDecimal("9.90")).withRating(4.0f).withTutor("Michael Spincer");
		assertCourseEqual(actualCourses.get(0), kubernates);
		
		Course boot = new Course("Spring Boot basics")
				.withCategory("JAV").withDescription("Spring Boot basics")
				.withPrice(new BigDecimal("9.90")).withRating(3.0f).withTutor("Ali Khan");
		assertCourseEqual(actualCourses.get(1), boot);
		
		Course junit = new Course("Introduction to Docker")
				.withCategory("JAV").withDescription("Introduction to Docker")
				.withPrice(new BigDecimal("9.90")).withRating(4.3f).withTutor("Michael Spincer");
		assertCourseEqual(actualCourses.get(2), junit);
	}

	@Test
	void whenExistingRelatedCourse_thenIncreaseVotes() {
		
		Course thisCourse = new Course("learn Java in 10 day!").withCategory("JAV");
		Course relatedCourse = new Course("Introduction to Kubernates").withCategory("JAV");		
		
		RelatedCourse result = courseService.addRelatedCourse(thisCourse, relatedCourse);
		
		assertThat(result.getCourse(), is(relatedCourse));
		assertThat(result.getVotes(), is(79));
	}
	
	@Test
	void givenVersioning_whenExistingRelatedCourse_thenNoConcurrentAccess() {
		
		Course thisCourse = new Course("learn Java in 10 day!").withCategory("JAV");
		Course relatedCourse = new Course("Introduction to Kubernates").withCategory("JAV");		
		
		Assertions.assertThrows(OptimisticLockException.class, () -> {
			
			courseService.addRelatedCourse(thisCourse, relatedCourse);
			
			jdbcTemplate.update("update related_courses rc set rc.votes=rc.votes+1, rc.version=rc.version+1 "
					+ "where rc.course=? and related_to=?", 1, 3);
			
			em.flush();
		});
		
	}
	
	@Test
	void whenNewRelatedCourse_thenSave() {
		
		Course thisCourse = new Course("learn Java in 10 day!").withCategory("JAV");
		Course relatedCourse = new Course("Java 11").withCategory("JAV");		
		
		RelatedCourse result = courseService.addRelatedCourse(thisCourse, relatedCourse);
		
		assertThat(result.getCourse(), is(relatedCourse));
		assertThat(result.getVotes(), is(1));
	}
	
	private void assertCourseEqual(Course actualCourse, Course kubernates) {
		assertThat(actualCourse, equalTo(kubernates));
		assertThat(actualCourse.getCategoryId(), equalTo(kubernates.getCategoryId()));
		assertThat(actualCourse.getDescription(), equalTo(kubernates.getDescription()));
		assertThat(actualCourse.getPrice(), equalTo(kubernates.getPrice()));
		assertThat(actualCourse.getRating(), equalTo(kubernates.getRating()));
		assertThat(actualCourse.getTutor(), equalTo(kubernates.getTutor()));
	}
}
