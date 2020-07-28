package com.edume.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "CourseCategory")
@Table(name = "course_category")
@Getter @Setter @NoArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CourseCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NaturalId
	@Column(name = "category_id", unique = true, nullable = false)
	private String categoryId;
	
	@Column(name = "category_name", unique = true, nullable = false)
	private String categoryName;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_category")
	private CourseCategory parentCategory;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentCategory", orphanRemoval = true)
	private List<CourseCategory> subCategories;
	
	public CourseCategory(String categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
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

	@Override
	public String toString() {
		return "CourseCategory [id=" + id + ", categoryId=" + categoryId + ", categoryName=" + categoryName
				+ ", subCategories=" + subCategories + "]";
	}
}
