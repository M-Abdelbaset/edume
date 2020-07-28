package com.edume.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.edume.entity.CourseCategoryEntity;
import com.edume.model.CourseCategoryHolder;
import com.edume.repository.CoursesRepository;

@Service
public class CoursesService {

	@Autowired CoursesRepository coursesRepo;
	
//	@Cacheable
	public CourseCategoryHolder getCategories() {		
		return null;
	}
}
