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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.edume.EdumeApplication;
import com.edume.entity.CourseCategoryEntity;
import com.edume.model.CourseCategoryHolder;
import com.edume.model.CourseCategoryHolder.CourseCategory;
import com.edume.repository.CourseCategoryRepository;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
class CourseCategoryServiceUnitTest {
	
	@Configuration
	@Import({EdumeApplication.CacheConfiguration.class})
	@AutoConfigureCache
	@ComponentScan(basePackageClasses = CourseCategoryService.class)
	static class Config {}
	
	@Autowired CourseCategoryService coursesService;
	@MockBean CourseCategoryRepository coursesRepo;
	
	@Test
	void whenGetCategories_thenReturnAll() {
		
		CourseCategoryEntity software = new CourseCategoryEntity("SWE").withCategoryId(1).withCategoryName("Software").withParentCategory(null);
		CourseCategoryEntity dev = new CourseCategoryEntity("DEV").withCategoryId(2).withCategoryName("Software development").withParentCategory(software);
		CourseCategoryEntity java = new CourseCategoryEntity("JAV").withCategoryId(3).withCategoryName("Java programming").withParentCategory(dev);
		CourseCategoryEntity boot = new CourseCategoryEntity("SPR").withCategoryId(4).withCategoryName("spring boot").withParentCategory(java);
		CourseCategoryEntity python = new CourseCategoryEntity("PYT").withCategoryId(5).withCategoryName("Python programming").withParentCategory(dev);
		CourseCategoryEntity c = new CourseCategoryEntity("C").withCategoryId(6).withCategoryName("C programming").withParentCategory(dev);
		CourseCategoryEntity economics = new CourseCategoryEntity("EC").withCategoryId(7).withCategoryName("Economics").withParentCategory(null);
		CourseCategoryEntity sports = new CourseCategoryEntity("SP").withCategoryId(8).withCategoryName("Sports").withParentCategory(null);
		CourseCategoryEntity media = new CourseCategoryEntity("MD").withCategoryId(9).withCategoryName("Media").withParentCategory(null);
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
