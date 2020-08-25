package com.edume.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;

@Embeddable @Getter
public class RelatedCoursesId implements Serializable {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "course")
	@OneToOne(fetch = FetchType.LAZY)
	private CourseEntity course;
	
	@JoinColumn(name = "related_to")
	@OneToOne(fetch = FetchType.LAZY)
	private CourseEntity relatedTo;
	
	public RelatedCoursesId(CourseEntity course, CourseEntity relatedTo) {
		this.course = course;
		this.relatedTo = relatedTo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof RelatedCoursesId))
			return false;
		else {
			RelatedCoursesId that = (RelatedCoursesId) obj;
			return Objects.equals(this.getCourse(), that.getCourse()) &&
					Objects.equals(this.getRelatedTo(), that.getRelatedTo());
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.course, this.relatedTo);
	}
}
