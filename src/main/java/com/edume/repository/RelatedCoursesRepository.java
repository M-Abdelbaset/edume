package com.edume.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edume.entity.CourseEntity;
import com.edume.entity.RelatedCoursesEntity;
import com.edume.entity.RelatedCoursesId;

@Repository
public interface RelatedCoursesRepository extends JpaRepository<RelatedCoursesEntity, RelatedCoursesId> {

	@Query("select c from CourseEntity c join RelatedCoursesEntity rc "
			+ "on c.id = rc.relatedCoursesId.relatedTo "
			+ "where rc.relatedCoursesId.course.id = ?1 "
			+ "order by rc.votes DESC")
	public List<CourseEntity> findRelatedCourses(Long courseId, Pageable page);
}
