package com.edume.entity;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;

@Entity(name = "RelatedCoursesEntity")
@Table(name = "related_courses")
@Getter
public class RelatedCoursesEntity {

	@EmbeddedId
	private RelatedCoursesId relatedCoursesId;
	
	private int votes = 1;
	
	@Version
	private Short version;
	
	RelatedCoursesEntity(){}
	
	public RelatedCoursesEntity(RelatedCoursesId relatedCoursesId){
		this.relatedCoursesId = relatedCoursesId;
	}
	
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		else if(!(obj instanceof RelatedCoursesEntity))
			return false;
		else {
			RelatedCoursesEntity that = (RelatedCoursesEntity) obj;
			return Objects.equals(this.getRelatedCoursesId(), that.getRelatedCoursesId()); // id is assigned manually not by ORM, can't be null at any time
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
