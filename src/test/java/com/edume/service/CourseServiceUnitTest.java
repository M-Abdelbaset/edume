package com.edume.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.edume.entity.CourseCategoryEntity;
import com.edume.entity.CourseEntity;
import com.edume.entity.RelatedCoursesEntity;
import com.edume.entity.RelatedCoursesId;
import com.edume.model.Course;
import com.edume.model.RelatedCourse;
import com.edume.repository.JPADataAccess;
import com.edume.repository.RelatedCoursesRepository;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class CourseServiceUnitTest {

	@InjectMocks CourseService courseService;
	@Mock RelatedCoursesRepository relatedCoursesRepository;
	@Mock JPADataAccess jpaDataAccess;
	
	@Test
	void whenGetRelatedCourses_ThenReturnTop3() {
		
		// mock courses entities
		List<CourseEntity> mockCoursesEntites = List.of(
				new CourseEntity("Introduction to Kubernates").withCategory(new CourseCategoryEntity("JAV")), 
				new CourseEntity("Spring Boot basics").withCategory(new CourseCategoryEntity("JAV")), 
				new CourseEntity("Junit").withCategory(new CourseCategoryEntity("JAV")));
		
		// given
		long courseId = 1l;		
		when(relatedCoursesRepository.findRelatedCourses(courseId, PageRequest.of(0, 3))).thenReturn(mockCoursesEntites);
		
		List<Course> actualCourses = courseService.getRelatedCourses(courseId);

		assertThat(actualCourses.size(), is(3));
		assertThat(actualCourses.get(0), equalTo(new Course("Introduction to Kubernates")));
		assertThat(actualCourses.get(1), equalTo(new Course("Spring Boot basics")));
		assertThat(actualCourses.get(2), equalTo(new Course("Junit")));
	}
	
	@Test
	void whenCourseNotSameCategoryOrSameCourse_thenNotRelated() {
		Course thisCourse = new Course("Java basics").withCategory("JAV");
		Course sameCourse = new Course("Java basics").withCategory("JAV");
		Course nonRelatedCourse = new Course("Economics").withCategory("EC");
		
		RelatedCourse relatedCourse = courseService.addRelatedCourse(thisCourse, nonRelatedCourse);
		assertThat(relatedCourse, nullValue());
		
		relatedCourse = courseService.addRelatedCourse(thisCourse, sameCourse);
		assertThat(relatedCourse, nullValue());
	}
	
	@Test
	void givenCourseInSameCategory_whenNew_thenSave() {
		
		// 2 courses in same category
		Course thisCourse = new Course("Java basics").withCategory("JAV");
		Course relatedCourse = new Course("Java in Action").withCategory("JAV");		
		
		// the 2 corresponding entities
		CourseEntity courseEntity1 = new CourseEntity("Java basics");
		CourseEntity courseEntity2 = new CourseEntity("Java in Action");
		
		when(jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, "Java basics")).thenReturn(courseEntity1);
		when(jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, "Java in Action")).thenReturn(courseEntity2);
		
		RelatedCoursesId relatedCoursesId = new RelatedCoursesId(courseEntity1, courseEntity2);
		RelatedCoursesEntity relatedCoursesEntity = new RelatedCoursesEntity(relatedCoursesId);
				
		// return empty optional for this id 
		when(relatedCoursesRepository.findById(relatedCoursesId)).thenReturn(Optional.empty());
		
		RelatedCoursesEntity savedCoursesEntity = new RelatedCoursesEntity(relatedCoursesId);
		when(relatedCoursesRepository.save(relatedCoursesEntity)).thenReturn(savedCoursesEntity);
		
		// test
		RelatedCourse result = courseService.addRelatedCourse(thisCourse, relatedCourse);
		
		// verify that save was called once
		verify(relatedCoursesRepository, times(1)).save(relatedCoursesEntity);
		assertThat(result.getCourse(), is(relatedCourse));
		assertThat(result.getVotes(), is(1));
	}
	
	@Test
	void givenCourseInSameCategory_whenExisting_thenIncreaseVotes() {
		
		// 2 courses in same category
		Course thisCourse = new Course("Java basics").withCategory("JAV");
		Course relatedCourse = new Course("Java in Action").withCategory("JAV");		
		
		// the 2 corresponding entities
		CourseEntity courseEntity1 = new CourseEntity("Java basics");
		CourseEntity courseEntity2 = new CourseEntity("Java in Action");
		
		when(jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, "Java basics")).thenReturn(courseEntity1);
		when(jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, "Java in Action")).thenReturn(courseEntity2);
		
		RelatedCoursesId relatedCoursesId = new RelatedCoursesId(courseEntity1, courseEntity2);
		RelatedCoursesEntity relatedCoursesEntity = new RelatedCoursesEntity(relatedCoursesId);
		
		when(relatedCoursesRepository.findById(relatedCoursesId)).thenReturn(Optional.of(relatedCoursesEntity));
		
		// test
		RelatedCourse result = courseService.addRelatedCourse(thisCourse, relatedCourse);
		
		// verify that save was called once
		assertThat(result.getCourse(), is(relatedCourse));
		assertThat(result.getVotes(), is(relatedCoursesEntity.getVotes()+1));
	}
}
