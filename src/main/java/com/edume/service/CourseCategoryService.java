package com.edume.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edume.entity.CourseCategoryEntity;
import com.edume.model.CourseCategoryHolder;
import com.edume.model.CourseCategoryHolder.CourseCategory;
import com.edume.repository.CourseCategoryRepository;

@Service
public class CourseCategoryService {

	@Autowired
	private CourseCategoryRepository coursesRepo;

	@Cacheable("categories-cache")
	@Transactional(readOnly = true)
	public CourseCategoryHolder getCategories() {
		List<CourseCategoryEntity> CategoriesEntities = coursesRepo.findAll();
		// convert entities to models ...
		List<CourseCategory> allCategories = getAllCategories(CategoriesEntities);

		return new CourseCategoryHolder(allCategories);
	}

	private List<CourseCategory> getAllCategories(List<CourseCategoryEntity> categoryEntities) {

		Map<String, CourseCategory> categoriesMap = new HashMap<>();
		List<CourseCategory> topNodes = new ArrayList<>();
		
		for(CourseCategoryEntity entity : categoryEntities) {
			categoriesMap.put(entity.getCategoryId(), convert(entity));
		}
		
		for(CourseCategoryEntity entity : categoryEntities) {
			if(entity.getParentCategory() == null)
				topNodes.add(categoriesMap.get(entity.getCategoryId()));
		}

		for (CourseCategoryEntity e1 : categoryEntities) {
			List<CourseCategory> subCategories = categoryEntities
					.stream()
					.filter(o -> e1.equals(o.getParentCategory()))
					.map(o -> categoriesMap.get(o.getCategoryId()))
					.collect(Collectors.toList());

			categoriesMap.get(e1.getCategoryId()).setSubCategories(subCategories);
		}

		return topNodes;
	}

	private CourseCategory convert(CourseCategoryEntity categoryEntity) {
		return new CourseCategory(categoryEntity.getCategoryId(), categoryEntity.getCategoryName());
	}
}
