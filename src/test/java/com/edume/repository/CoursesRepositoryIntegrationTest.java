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
class CoursesRepositoryIntegrationTest {

	@Autowired CoursesRepository coursesRepository;
	@Autowired JdbcTemplate testTemplate;
	
	@Test
	@Sql(scripts = {"classpath:categories.sql"})
	void whenQueryCategories_thenReturnAll() {
		// setup
		int rowsCount = JdbcTestUtils.countRowsInTable(testTemplate, "course_category");

		// when
		List<CourseCategoryEntity> actualCategories = coursesRepository.findAll();
		
		// then
		assertThat(actualCategories.size(), is(rowsCount));
	}
}
