package com.edume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edume.model.CourseCategoryHolder;
import com.edume.service.CoursesService;

@RestController
public class CoursesController {

	private static final String BASE_PATH = "/api/courses";
	public static final String CATEGORIES_PATH = BASE_PATH + "/category";
	
	@Autowired private CoursesService coursesService;
	
	@GetMapping(path = CoursesController.CATEGORIES_PATH, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CourseCategoryHolder> getCoursesCategories(){
		return ResponseEntity.ok().body(coursesService.getCategories());
	}
}
