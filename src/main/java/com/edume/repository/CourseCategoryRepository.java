package com.edume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edume.entity.CourseCategoryEntity;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategoryEntity, Integer> {

}
