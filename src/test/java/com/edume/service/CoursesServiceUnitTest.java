package com.edume.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edume.entity.CourseCategoryEntity;
import com.edume.model.CourseCategoryHolder;
import com.edume.model.CourseCategoryHolder.CourseCategory;
import com.edume.repository.CoursesRepository;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class CoursesServiceUnitTest {

	@InjectMocks
	CoursesService coursesService = new CoursesService();
	
	@Mock CoursesRepository coursesRepo;
	
	@Test
	void whenGetCategories_thenReturnAll() {
	
		// setup
		CourseCategoryEntity software = new CourseCategoryEntity(1, "SWE", "Software", null, null);
		CourseCategoryEntity dev = new CourseCategoryEntity(2, "DEV", "Software development", software, null);
		CourseCategoryEntity java = new CourseCategoryEntity(3, "JAV", "Java programming", dev, null);
		CourseCategoryEntity boot = new CourseCategoryEntity(4, "SPR", "spring boot", java, null);
		CourseCategoryEntity python = new CourseCategoryEntity(5, "PYT", "Python programming", dev, null);
		CourseCategoryEntity c = new CourseCategoryEntity(6, "C", "C programming", dev, null);
		CourseCategoryEntity economics = new CourseCategoryEntity(7, "EC", "Economics", null, null);
		CourseCategoryEntity sports = new CourseCategoryEntity(8, "SP", "Sports", null, null);
		CourseCategoryEntity media = new CourseCategoryEntity(9, "MD", "Media", null, null);
		List<CourseCategoryEntity> entities = List.of(software, dev, java, boot, python, c, economics, sports, media);
		
		when(coursesRepo.findAll()).thenReturn(entities);
		
		// when
		CourseCategoryHolder categories = coursesService.getCategories();
		
		// then
		assertThat(categories, not(nullValue()));
		assertThat(categories.getCategories(), not(nullValue()));
		assertThat(categories.getCategories().size(), is(4));
		assertThat(categories.getCategories(), hasItems(
				new CourseCategory("SWE", ""), 
				new CourseCategory("EC", ""), 
				new CourseCategory("SP", ""), 
				new CourseCategory("MD", ""))
		);
		
		CourseCategory swCategory = categories.getCategories().get(0);
		assertThat(swCategory.getSubCategories(), hasItem(
				new CourseCategory("DEV", ""))
		);
		
		CourseCategory devCategory = swCategory.getSubCategories().get(0);
		assertThat(devCategory.getSubCategories(), hasItems(
				new CourseCategory("JAV", ""),
				new CourseCategory("PYT", ""),
				new CourseCategory("C", ""))
		);
		
		CourseCategory javCategory = devCategory.getSubCategories().get(0);
		assertThat(javCategory.getSubCategories(), hasItem(
				new CourseCategory("SPR", ""))
		);
	}
}
