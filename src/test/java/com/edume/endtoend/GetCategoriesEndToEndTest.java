package com.edume.endtoend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.edume.controller.CoursesController;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GetCategoriesEndToEndTest {

	@Autowired MockMvc mockMvc;
	
	@Test
	@Transactional
	@Sql(scripts = {"classpath:categories.sql"})
	void whenGetCategories_thenReturnAll() throws Exception {
		
		String expectedJson = "{\"categories\":[{\"categoryId\":\"SWE\",\"categoryName\":\"Software\",\"subCategories\":[{\"categoryId\":\"DEV\",\"categoryName\":\"Software development\",\"subCategories\":[{\"categoryId\":\"JAV\",\"categoryName\":\"Java programming\",\"subCategories\":[{\"categoryId\":\"SPR\",\"categoryName\":\"spring boot\",\"subCategories\":[]}]},{\"categoryId\":\"PYT\",\"categoryName\":\"Python programming\",\"subCategories\":[]},{\"categoryId\":\"C\",\"categoryName\":\"C programming\",\"subCategories\":[]}]}]},{\"categoryId\":\"EC\",\"categoryName\":\"Economics\",\"subCategories\":[]},{\"categoryId\":\"SP\",\"categoryName\":\"Sports\",\"subCategories\":[]},{\"categoryId\":\"MD\",\"categoryName\":\"Media\",\"subCategories\":[]}]}";
		
		mockMvc.perform(get(CoursesController.CATEGORIES_PATH))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json(expectedJson));
	}
}
