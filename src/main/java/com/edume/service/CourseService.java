package com.edume.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.edume.entity.CourseEntity;
import com.edume.entity.RelatedCoursesEntity;
import com.edume.entity.RelatedCoursesId;
import com.edume.model.Course;
import com.edume.model.RelatedCourse;
import com.edume.repository.JPADataAccess;
import com.edume.repository.RelatedCoursesRepository;

@Service
public class CourseService {

	@Autowired private RelatedCoursesRepository relatedCoursesRepository;
	@Autowired private JPADataAccess jpaDataAccess;
	
	public List<Course> getRelatedCourses(Long courseId) {
		List<CourseEntity> relatedCourses = 
				relatedCoursesRepository.findRelatedCourses(courseId, PageRequest.of(0, 3));
		return convert(relatedCourses);
	}
	
	public RelatedCourse addRelatedCourse(Course thisCourse, Course relatedCourse) {
	
		if(relatedCourse.getCategoryId().equals(thisCourse.getCategoryId())
				&& !(relatedCourse.equals(thisCourse))) {
			
			RelatedCoursesId relatedCoursesId = new RelatedCoursesId(
							jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, thisCourse.getName()), 
							jpaDataAccess.getReferenceByNaturalId(CourseEntity.class, relatedCourse.getName()));
			
			Optional<RelatedCoursesEntity> relatedCoursesRecord = 
					relatedCoursesRepository.findById(relatedCoursesId);
			
			if(relatedCoursesRecord.isEmpty()) {
				RelatedCoursesEntity newRelatedCoursesEntity = 
						relatedCoursesRepository.save(new RelatedCoursesEntity(relatedCoursesId));
				return new RelatedCourse(relatedCourse, newRelatedCoursesEntity.getVotes());
			} else {
				RelatedCoursesEntity existingRelatedCoursesEntity = relatedCoursesRecord.get();
				existingRelatedCoursesEntity.setVotes(existingRelatedCoursesEntity.getVotes() + 1);
				return new RelatedCourse(relatedCourse, existingRelatedCoursesEntity.getVotes());
			}
		}
		
		return null;
	}
	
	private List<Course> convert(List<CourseEntity> courseEntities) {
		
		List<Course> courses = new ArrayList<>();
		
		for(CourseEntity entity: courseEntities) {
			courses.add(new Course(entity.getName())
					.withCategory(entity.getCategory().getCategoryId())
					.withDescription(entity.getDescription())
					.withPrice(entity.getPrice())
					.withRating(entity.getRating())
					.withTutor(entity.getTutor()));
		}

		return courses;
	}
	
	public void test(String str) {
		
	}
}
