package com.edume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RelatedCourse {

	private Course course;
	private int votes;
}
