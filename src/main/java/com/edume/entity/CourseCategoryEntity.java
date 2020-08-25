package com.edume.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;

@Entity(name = "CourseCategoryEntity")
@Table(name = "course_category")
@Getter
public class CourseCategoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NaturalId
	@NotNull
	@Column(name = "category_id", unique = true, nullable = false)
	private String categoryId;
	
	@Column(name = "category_name", unique = true, nullable = false)
	@NotNull
	private String categoryName;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_category")
	private CourseCategoryEntity parentCategory;
	
	@Version
	private Short version;
	
	CourseCategoryEntity() {}
	
	public CourseCategoryEntity(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public CourseCategoryEntity withCategoryId(Integer id) {
		this.id = id;
		return this;
	}
	
	public CourseCategoryEntity withCategoryName(String name) {
		this.categoryName = name;
		return this;
	}
	
	public CourseCategoryEntity withParentCategory(CourseCategoryEntity parentCategory) {
		this.parentCategory = parentCategory;
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof CourseCategoryEntity))
			return false;
		CourseCategoryEntity that = (CourseCategoryEntity) obj;
		return this.getCategoryId().equalsIgnoreCase(that.getCategoryId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getCategoryId());
	}
}
