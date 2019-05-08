package com.spring.store.service.impl;

import com.spring.store.dao.entities.CategoryEntity;
import com.spring.store.dao.models.CategoryModel;
import com.spring.store.dao.repos.CategoryRepository;
import com.spring.store.mappers.CategoryMapper;
import com.spring.store.service.api.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CategoryModel save(CategoryModel model) {
        CategoryEntity categoryEntity = mapper.toEntity(model);
        CategoryEntity savedEntity = repository.save(categoryEntity);
        return mapper.toModel(savedEntity);
    }

    @Override
    public CategoryModel get(String id) {
        CategoryEntity categoryEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Category found with this id %s",id)));
        return mapper.toModel(categoryEntity);
    }

    @Override
    public List<CategoryModel> list() {
        List<CategoryEntity> categoryEntities = repository.findAll();
        return mapper.toModels(categoryEntities);
    }

    @Override
    public CategoryModel delete(String id) {
        CategoryEntity categoryEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Category found with this id %s",id)));
        repository.delete(categoryEntity);
        return mapper.toModel(categoryEntity);
    }
}
