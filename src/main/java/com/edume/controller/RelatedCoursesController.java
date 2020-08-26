package com.edume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edume.model.Course;
import com.edume.model.RelatedCourse;
import com.edume.service.CourseService;

@RestController
public class RelatedCoursesController {

	private static final String BASE_PATH = "/api/courses";
	public static final String RELATED_COURSES_PATH = BASE_PATH + "/related";
	
	@Autowired private CourseService coursesService;
	
	@PostMapping(path = RelatedCoursesController.RELATED_COURSES_PATH, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RelatedCourse> addRelatedCourse(
			@RequestParam("courseName") String courseName,
			@RequestParam("courseCat") String courseCat,
			@RequestParam("relatedToName") String relatedToName,
			@RequestParam("relatedToCat") String relatedToCat) {
		
		Course c1 = new Course(courseName).withCategory(courseCat);
		Course c2 = new Course(relatedToName).withCategory(relatedToCat);
		RelatedCourse relatedCourse = coursesService.addRelatedCourse(c1, c2);
		
		if(relatedCourse == null)
			return ResponseEntity.badRequest().build();
		else
			return ResponseEntity.ok(relatedCourse);
	}
}
