package com.edume.model;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
public class CourseCategoryHolder {

	private final List<CourseCategory> categories;
	
	@Getter @Setter @ToString @RequiredArgsConstructor @AllArgsConstructor
	public static class CourseCategory {
		
		private final String categoryId;
		private final String categoryName;
		private List<CourseCategory> subCategories;
		
		@Override
		public boolean equals(Object obj) {
			if(obj == this)
				return true;
			if(!(obj instanceof CourseCategory))
				return false;
			CourseCategory that = (CourseCategory) obj;
			return this.getCategoryId().equalsIgnoreCase(that.getCategoryId());
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.getCategoryId());
		}
	}
}
