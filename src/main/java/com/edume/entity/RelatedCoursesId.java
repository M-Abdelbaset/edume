package com.edume.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class RelatedCoursesId implements Serializable {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "course")
	@OneToOne
	private CourseEntity course;
	
	@JoinColumn(name = "related_to")
	@OneToOne
	private CourseEntity relatedTo;
	
	public RelatedCoursesId(Long courseId, Long relatedTo) {
		this.course = new CourseEntity(null).withId(courseId);
		this.relatedTo = new CourseEntity(null).withId(relatedTo);
	}
	
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
