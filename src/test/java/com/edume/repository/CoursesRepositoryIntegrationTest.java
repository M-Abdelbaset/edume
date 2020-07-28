package com.edume.repository;

import static org.hamcrest.Matchers.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import com.edume.entity.CourseCategory;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
@Sql(scripts = {"classpath:categories.sql"})
class CoursesRepositoryIntegrationTest {

	@Autowired CoursesRepository coursesRepository;
	
	@Test
	void whenQueryCategories_thenReturnAll() {
		
		// setup
		CourseCategory software = new CourseCategory("SWE", "Software");
		CourseCategory dev = new CourseCategory("DEV", "Software development");
		CourseCategory java = new CourseCategory("JAV", "Java programming");
		CourseCategory boot = new CourseCategory("SPR", "spring boot");
		CourseCategory python = new CourseCategory("PYT", "Python programming");
		CourseCategory c = new CourseCategory("C", "C programming");
		CourseCategory economics = new CourseCategory("EC", "Economics");
		CourseCategory sports = new CourseCategory("SP", "Sports");
		CourseCategory media = new CourseCategory("MD", "Media");
		
		// when
		List<CourseCategory> actualCategories = coursesRepository.findByParentCategoryIsNull();
		
		// then
		assertThat(actualCategories.size(), is(4));
		assertThat(actualCategories, hasItems(software, economics, sports, media)); // top nodes
		
		// check software sub categories
		int swIndex = actualCategories.indexOf(software);
		CourseCategory swCat = actualCategories.get(swIndex); 
		assertThat(swCat.getSubCategories().size(), is(1));
		assertThat(swCat.getSubCategories(), hasItem(dev));
		
		// check dev sub categories
		int devIndex = swCat.getSubCategories().indexOf(dev);
		CourseCategory devCat = swCat.getSubCategories().get(devIndex);
		assertThat(devCat.getSubCategories().size(), is(3));
		assertThat(devCat.getSubCategories(), hasItems(java, python, c));
		
		// check java sub categories
		int javaIndex = devCat.getSubCategories().indexOf(java);
		CourseCategory javaCat = devCat.getSubCategories().get(javaIndex);
		assertThat(javaCat.getSubCategories().size(), is(1));
		assertThat(javaCat.getSubCategories(), hasItem(boot));
	}
	
	@Test
	void whenCategoriesPreviouslyLoaded_thenUse2ndLevelCache(){
		
	}
}
