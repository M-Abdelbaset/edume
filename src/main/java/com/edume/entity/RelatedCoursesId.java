package com.edume.entity;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Embeddable
public class RelatedCoursesId {

	@Column(name = "course")
	@OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, mappedBy = "id")
	private CourseEntity course;
	
	@Column(name = "related_to")
	@OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, mappedBy = "id")
	private CourseEntity relatedTo;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof RelatedCoursesId))
			return false;
		else {
			RelatedCoursesId that = (RelatedCoursesId) obj;
			return Objects.equals(this, that);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.course, this.relatedTo);
	}
}
