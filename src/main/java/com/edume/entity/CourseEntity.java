package com.edume.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity(name = "CourseEntity")
@Table(name = "course")
@Getter @RequiredArgsConstructor
public class CourseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NaturalId
	@NotEmpty
	@Column(unique = true, nullable = false)
	private final String name;
	
	@NotEmpty
	private String description;
	
	@NotNull
	private BigDecimal price;
	
	@NotEmpty
	private String tutor;
	
	private float rating;
	
	public CourseEntity withDescription(String description) {
		this.description = description;
		return this;
	}
	
	public CourseEntity withPrice(BigDecimal price) {
		this.price = price;
		return this;
	}
	
	public CourseEntity withTutor(String tutor) {
		this.tutor = tutor;
		return this;
	}
	
	public CourseEntity withRating(float rating) {
		this.rating = rating;
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == this)
			return true;
		if(!(obj instanceof CourseEntity))
			return false;
		else {
			CourseEntity that = (CourseEntity) obj;
			return Objects.equals(getName(), that.getName());
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
