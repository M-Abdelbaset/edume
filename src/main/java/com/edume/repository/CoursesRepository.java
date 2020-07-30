package com.edume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edume.entity.CourseCategoryEntity;

@Repository
public interface CoursesRepository extends JpaRepository<CourseCategoryEntity, Integer> {

	@Transactional(readOnly = true)
	List<CourseCategoryEntity> findAll();
}
