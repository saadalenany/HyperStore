package com.spring.store.service.impl;

import com.spring.store.dao.entities.AdminEntity;
import com.spring.store.dao.entities.CategoryEntity;
import com.spring.store.dao.entities.ProductEntity;
import com.spring.store.dao.models.ProductModel;
import com.spring.store.dao.repos.AdminRepository;
import com.spring.store.dao.repos.CategoryRepository;
import com.spring.store.dao.repos.ProductRepository;
import com.spring.store.mappers.ProductMapper;
import com.spring.store.service.api.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    private final AdminRepository adminRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, AdminRepository adminRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.adminRepository = adminRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductModel save(ProductModel model) {
        ProductEntity productEntity = mapper.toEntity(model);
        ProductEntity savedEntity = repository.save(productEntity);
        return mapper.toModel(savedEntity);
    }

    @Override
    public ProductModel get(String id) {
        ProductEntity productEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Product found with this id %s",id)));
        return mapper.toModel(productEntity);
    }

    @Override
    public List<ProductModel> listByAdmin(String adminId) {
        AdminEntity adminEntity = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException(String.format("No Admin found with this id %s",adminId)));
        List<ProductEntity> productEntities = repository.getProductsByAdmin(adminEntity);
        return mapper.toModels(productEntities);
    }

    @Override
    public List<ProductModel> listByCategory(String categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException(String.format("No Category found with this id %s",categoryId)));
        List<ProductEntity> productEntities = repository.getProductsByCategory(categoryEntity);
        return mapper.toModels(productEntities);
    }

    @Override
    public List<ProductModel> listByDate(LocalDateTime creationDate) {
        List<ProductEntity> productsFromSpecificDate = repository.getProductsFromSpecificDate(creationDate);
        return mapper.toModels(productsFromSpecificDate);
    }

    @Override
    public List<ProductModel> listByLastWeek(LocalDateTime creationDate) {
        List<ProductEntity> productsFromLast7Days = repository.getProductsFromLast7Days(creationDate.minusDays(7));
        return mapper.toModels(productsFromLast7Days);
    }

    @Override
    public ProductModel delete(String id) {
        ProductEntity productEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Product found with this id %s",id)));
        repository.delete(productEntity);
        return mapper.toModel(productEntity);
    }
}
