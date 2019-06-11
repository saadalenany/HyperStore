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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        AdminEntity adminEntity = adminRepository.findById(model.getAdminId()).orElseThrow(() -> new RuntimeException(String.format("No Admin found with this id")));
        CategoryEntity categoryEntity = categoryRepository.findById(model.getCategoryId()).orElseThrow(() -> new RuntimeException(String.format("No Category found with this id")));
        productEntity.setAdmin(adminEntity);
        productEntity.setCategory(categoryEntity);
        ProductEntity savedEntity = repository.save(productEntity);
        return mapper.toModel(savedEntity);
    }

    @Override
    public ProductModel get(String id) {
        ProductEntity productEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Product found with this id %s", id)));
        return mapper.toModel(productEntity);
    }

    @Override
    public List<ProductModel> findByAdmin(String adminId) {
        AdminEntity adminEntity = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException(String.format("No Admin found with this id %s", adminId)));
        List<ProductEntity> productEntities = repository.getProductsByAdmin(adminEntity);
        return mapper.toModels(productEntities);
    }

    @Override
    public List<ProductModel> findByCategory(String categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException(String.format("No Category found with this id %s", categoryId)));
        List<ProductEntity> productEntities = repository.findByCategory(categoryEntity);
        return mapper.toModels(productEntities);
    }

    @Override
    public List<ProductModel> findByCategoryAsPage(String categoryId, Integer page, Integer size) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException(String.format("No Category found with this id %s", categoryId)));
        Pageable pageWithElements = PageRequest.of(page, size);
        List<ProductEntity> productEntities = repository.findByCategory(categoryEntity, pageWithElements);
        return mapper.toModels(productEntities);
    }

    @Override
    public List<ProductModel> findByDate(LocalDateTime creationDate) {
        List<ProductEntity> productsFromSpecificDate = repository.getProductsFromSpecificDate(creationDate);
        return mapper.toModels(productsFromSpecificDate);
    }

    @Override
    public List<ProductModel> findByLastWeek(LocalDateTime creationDate) {
        List<ProductEntity> productsFromLast7Days = repository.getProductsFromLast7Days(creationDate.minusDays(7));
        return mapper.toModels(productsFromLast7Days);
    }

    @Override
    public List<ProductModel> findByRate(Integer rate) {
        List<ProductEntity> byRate = repository.getByRate(rate);
        return mapper.toModels(byRate);
    }

    @Override
    public List<ProductModel> findByDiscount(Integer discount) {
        List<ProductEntity> byDiscount = repository.getByDiscount(discount);
        return mapper.toModels(byDiscount);
    }

    @Override
    public List<ProductModel> findByPriceBetweenAsPage(Integer low, Integer high, Integer page, Integer size) {
        Pageable pageWithElements = PageRequest.of(page, size);
        List<ProductEntity> byPriceBetween = repository.getByPriceBetweenAsPage(low, high, pageWithElements);
        return mapper.toModels(byPriceBetween);
    }

    @Override
    public List<ProductModel> findByPriceBetween(Integer low, Integer high) {
        List<ProductEntity> byPriceBetween = repository.getByPriceBetween(low, high);
        return mapper.toModels(byPriceBetween);
    }

    @Override
    public ProductModel delete(String id) {
        ProductEntity productEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Product found with this ID %s", id)));
        repository.delete(productEntity);
        return mapper.toModel(productEntity);
    }

    @Override
    public Integer getProductCount() {
        return repository.getProductCount();
    }

    @Override
    public List<ProductModel> findAll() {
        List<ProductEntity> all = repository.findAll();
        return mapper.toModels(all);
    }

    @Override
    public List<ProductModel> findAllAsPage(Integer page, Integer size) {
        Pageable pageWithElements = PageRequest.of(page, size);
        List<ProductEntity> asPage = repository.findAll(pageWithElements).getContent();
        return mapper.toModels(asPage);
    }

    @Override
    public List<ProductModel> findByRateAsPage(Integer rate, Integer page, Integer size, String exceptProduct) {
        Pageable pageWithElements = PageRequest.of(page, size);
        List<ProductEntity> asPage = repository.getByRateAsPage(rate, exceptProduct, pageWithElements);
        return mapper.toModels(asPage);
    }
}
