package com.edume.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.edume.EdumeApplication;
import com.edume.entity.CourseCategoryEntity;
import com.edume.model.CourseCategoryHolder;
import com.edume.model.CourseCategoryHolder.CourseCategory;
import com.edume.repository.CoursesRepository;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
class CoursesServiceUnitTest {
	
	@Configuration
	@Import({EdumeApplication.CacheConfig.class})
	@AutoConfigureCache
	@ComponentScan(basePackageClasses = CoursesService.class)
	static class Config {}
	
	@Autowired CoursesService coursesService;
	@MockBean CoursesRepository coursesRepo;
	
	@Test
	void whenGetCategories_thenReturnAll() {
		
		CourseCategoryEntity software = new CourseCategoryEntity(1, "SWE", "Software", null, null);
		CourseCategoryEntity dev = new CourseCategoryEntity(2, "DEV", "Software development", software, null);
		CourseCategoryEntity java = new CourseCategoryEntity(3, "JAV", "Java programming", dev, null);
		CourseCategoryEntity boot = new CourseCategoryEntity(4, "SPR", "spring boot", java, null);
		CourseCategoryEntity python = new CourseCategoryEntity(5, "PYT", "Python programming", dev, null);
		CourseCategoryEntity c = new CourseCategoryEntity(6, "C", "C programming", dev, null);
		CourseCategoryEntity economics = new CourseCategoryEntity(7, "EC", "Economics", null, null);
		CourseCategoryEntity sports = new CourseCategoryEntity(8, "SP", "Sports", null, null);
		CourseCategoryEntity media = new CourseCategoryEntity(9, "MD", "Media", null, null);
		List<CourseCategoryEntity> testEntities = List.of(software, dev, java, boot, python, c, economics, sports, media);
		
		when(coursesRepo.findAll()).thenReturn(testEntities);
		
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

	@Test
	void whenGetCategoriesCalledOnce_thenCacheCategories() {
		
		// when
		CourseCategoryHolder holder1 = coursesService.getCategories();		
		CourseCategoryHolder holder2 = coursesService.getCategories();
		CourseCategoryHolder holder3 = coursesService.getCategories();
		
		// then
		Assertions.assertTrue(holder1 == holder2); // same object returned
		Assertions.assertTrue(holder1 == holder3); // same object returned
	}
}
