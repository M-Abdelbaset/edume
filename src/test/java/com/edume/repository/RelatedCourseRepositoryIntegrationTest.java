package com.edume.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.edume.entity.CourseEntity;
import com.edume.entity.RelatedCoursesEntity;
import com.edume.entity.RelatedCoursesId;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
@Sql(scripts = {"classpath:categories.sql", "classpath:related_courses.sql"})
@ComponentScan(basePackageClasses = {JPADataAccess.class})
class RelatedCourseRepositoryIntegrationTest {

	@Autowired RelatedCoursesRepository relatedCoursesRepository;
	@Autowired JdbcTemplate jdbcTemplate;
	@Autowired EntityManager em;
	@Autowired JPADataAccess jpaDataAccess;
	
	@Test
	void whenQueryRelatedCourses_thenTopVotes3AreReturned() {
		
		List<CourseEntity> courses = 
				relatedCoursesRepository.findRelatedCourses(1l, PageRequest.of(0, 3));
		
		assertThat(courses.size(), is(3));
		assertThat(courses.get(0), equalTo(new CourseEntity("Introduction to Kubernates")));
		assertThat(courses.get(1), equalTo(new CourseEntity("Spring Boot basics")));
		assertThat(courses.get(2), equalTo(new CourseEntity("Introduction to Docker")));
	}

	@Test
	void givenRelatedCourseName_whenQuery_thenReturnId() {
		
		CourseEntity courseReference = 
				jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, "learn Java in 10 day!");
		assertThat(courseReference.getId(), is(1l));
	}
	
	@Test
	void whenRelatedCourseExist_thenReturn() {
		
		// get related courses references
		CourseEntity courseReference = jpaDataAccess
				.getReferenceByNaturalId(CourseEntity.class, "learn Java in 10 day!");
		CourseEntity relatedCourseReference = jpaDataAccess
				.getReferenceByNaturalId(CourseEntity.class, "Introduction to Docker");
	
		RelatedCoursesId relatedCoursesId = new RelatedCoursesId(courseReference, relatedCourseReference);
		
		Optional<RelatedCoursesEntity> relatedCoursesRecord = 
				relatedCoursesRepository.findById(relatedCoursesId);
		
		assertThat(relatedCoursesRecord.get(), notNullValue());
	}
	
	@Test
	void whenNoRelatedCourseExist_thenSaveNew() {
		
		// get related courses references
		CourseEntity courseReference = jpaDataAccess
				.getReferenceByNaturalId(CourseEntity.class, "learn Java in 10 day!");
		CourseEntity relatedCourseReference = jpaDataAccess
				.getReferenceByNaturalId(CourseEntity.class, "Junit");
	
		RelatedCoursesId relatedCoursesId = new RelatedCoursesId(courseReference, relatedCourseReference);
		
		int initialCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "related_courses");
		
		relatedCoursesRepository.save(new RelatedCoursesEntity(relatedCoursesId));
		relatedCoursesRepository.flush();
		
		int finalCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "related_courses");
		
		assertThat(finalCount, is(initialCount + 1));
	}
	
	@Test
	void whenUpdateVotes_thenIncreaseByOne() {

		RelatedCoursesId relatedCoursesId = new RelatedCoursesId(
				jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, "learn Java in 10 day!"), 
				jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, "Spring Boot basics"));

		RelatedCoursesEntity relatedCoursesRecord = 
				relatedCoursesRepository.findById(relatedCoursesId).get();

		int initialVotes = relatedCoursesRecord.getVotes();
		
		relatedCoursesRecord.setVotes(relatedCoursesRecord.getVotes() + 1);
		relatedCoursesRepository.flush();
		
		assertThat(relatedCoursesRecord.getVotes(), is(initialVotes + 1));
	}
}
