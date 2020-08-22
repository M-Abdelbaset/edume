package com.edume.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity(name = "RelatedCoursesEntity")
@Table(name = "related_courses")
@Getter @RequiredArgsConstructor
public class RelatedCoursesEntity {

	@EmbeddedId
	private final RelatedCoursesId relatedCoursesId;
	
	private int votes;
	
	@Version
	private Short version;
}
