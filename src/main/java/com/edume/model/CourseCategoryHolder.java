package com.edume.model;

import java.util.List;
import lombok.Data;

@Data
public class CourseCategoryHolder {

	private final List<CourseCategory> categories;
	
	@Data
	public static class CourseCategory {
		
		private final String categoryId;
		private final String categoryName;
		private final List<CourseCategory> subCategories;
		
	}
}
