package com.edume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edume.entity.RelatedCoursesEntity;
import com.edume.entity.RelatedCoursesId;

//@Repository
public interface RelatedCoursesRepository extends JpaRepository<RelatedCoursesEntity, RelatedCoursesId> {

}
