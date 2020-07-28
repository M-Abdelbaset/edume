package com.edume.repository;

import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.edume.entity.CourseCategoryEntity;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
@Sql(scripts = {"classpath:categories.sql"}) // data setup script
class CoursesRepositoryIntegrationTest {

	@Autowired CoursesRepository coursesRepository;
	@Autowired JdbcTemplate testTemplate;
	
	@Test
	void whenQueryCategories_thenReturnAll() {
		// setup
		int rowsCount = JdbcTestUtils.countRowsInTable(testTemplate, "course_category");

		// when
		List<CourseCategoryEntity> actualCategories = coursesRepository.findAll();
		
		// then
		assertThat(actualCategories.size(), is(rowsCount));
		
		/*
		// setup
		CourseCategoryEntity software = new CourseCategoryEntity().withId("SWE");
		CourseCategoryEntity dev = new CourseCategoryEntity().withId("DEV");
		CourseCategoryEntity java = new CourseCategoryEntity().withId("JAV");
		CourseCategoryEntity boot = new CourseCategoryEntity().withId("SPR");
		CourseCategoryEntity python = new CourseCategoryEntity().withId("PYT");
		CourseCategoryEntity c = new CourseCategoryEntity().withId("C");
		CourseCategoryEntity economics = new CourseCategoryEntity().withId("EC");
		CourseCategoryEntity sports = new CourseCategoryEntity().withId("SP");
		CourseCategoryEntity media = new CourseCategoryEntity().withId("MD");
		
		// when
		List<CourseCategoryEntity> actualCategories = coursesRepository.findAll();
		
		// then
		assertThat(actualCategories.size(), is(9));
		assertThat(actualCategories, hasItems(software, economics, sports, media)); // top nodes
		
		// check software sub categories
		int swIndex = actualCategories.indexOf(software);
		CourseCategoryEntity swCat = actualCategories.get(swIndex); 
		assertThat(swCat.getSubCategories().size(), is(1));
		assertThat(swCat.getSubCategories(), hasItem(dev));
		
		// check dev sub categories
		int devIndex = swCat.getSubCategories().indexOf(dev);
		CourseCategoryEntity devCat = swCat.getSubCategories().get(devIndex);
		assertThat(devCat.getSubCategories().size(), is(3));
		assertThat(devCat.getSubCategories(), hasItems(java, python, c));
		
		// check java sub categories
		int javaIndex = devCat.getSubCategories().indexOf(java);
		CourseCategoryEntity javaCat = devCat.getSubCategories().get(javaIndex);
		assertThat(javaCat.getSubCategories().size(), is(1));
		assertThat(javaCat.getSubCategories(), hasItem(boot));
		*/
	}
}
