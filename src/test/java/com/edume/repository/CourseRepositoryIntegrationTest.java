package com.edume.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("dev")
class CourseRepositoryIntegrationTest {

	@Autowired CourseRepository coursesRepository;
	@Autowired JdbcTemplate testTemplate;
	
	@Test
	@Sql(scripts = {"classpath:categories.sql", "classpath:courses.sql"})
	void whenQueryCategories_thenReturnAll() {
		
	}
}
