package com.edume.model;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @RequiredArgsConstructor @ToString
public class Course {
	
	private final String name;
	private String description;
	private BigDecimal price;
	private String tutor;
	private String categoryId;
	private float rating;
	
	public Course withDescription(String description) {
		this.description = description;
		return this;
	}
	
	public Course withPrice(BigDecimal price) {
		this.price = price;
		return this;
	}
	
	public Course withTutor(String tutor) {
		this.tutor = tutor;
		return this;
	}
	
	public Course withRating(float rating) {
		this.rating = rating;
		return this;
	}
	
	public Course withCategory(String categoryId) {
		this.categoryId = categoryId;
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == this)
			return true;
		if(!(obj instanceof Course))
			return false;
		else {
			Course that = (Course) obj;
			return Objects.equals(getName(), that.getName());
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
