package com.spring.store.controllers;

import com.spring.store.dao.entities.CategoryEntity;
import com.spring.store.dao.models.CategoryModel;
import com.spring.store.dao.repos.CategoryRepository;
import com.spring.store.utils.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CategoryControllerTest extends BaseTest {

    private final String path = "/category";

    @Autowired
    CategoryRepository repository;

    @Test
    void testCreateCategoryModel() throws Exception {

        CategoryModel order = createCategoryModel();
        CategoryModel responseCategory = postForObject(path, order, CategoryModel.class);

        final String categoryId = responseCategory.getId();
        runInTransaction(status -> {
            CategoryEntity categoryEntity = repository.findById(categoryId).orElseGet(() -> fail("Expected CategoryEntity not found"));

            assertEquals(categoryId, categoryEntity.getId(), "Category wasn't created successfully");
            return null;
        });

    }

    @Test
    void testUpdateCategoryModel() throws Exception {

        CategoryModel order = createCategoryModel();
        CategoryModel responseCategory = postForObject(path, order, CategoryModel.class);
        final String categoryId = responseCategory.getId();
        runInTransaction(status -> {
            CategoryEntity categoryEntity = repository.findById(categoryId).orElseGet(() -> fail("Expected CategoryEntity not found"));

            assertEquals(categoryId, categoryEntity.getId(), "Category wasn't created successfully");
            return null;
        });

        responseCategory.setName("test_name");
        responseCategory = putForObject(path, responseCategory, CategoryModel.class);
        assertEquals("test_name", responseCategory.getName(), "Name wasn't updated successfully");
    }

    @Test
    void testRetrieveCategoryModel() throws Exception {

        CategoryModel order = createCategoryModel();
        CategoryModel responseCategory = postForObject(path, order, CategoryModel.class);
        final String categoryId = responseCategory.getId();
        runInTransaction(status -> {
            CategoryEntity categoryEntity = repository.findById(categoryId).orElseGet(() -> fail("Expected CategoryEntity not found"));

            assertEquals(categoryId, categoryEntity.getId(), "Category wasn't created successfully");
            return null;
        });

        CategoryModel retrievedModel = getForObject(path+"/"+categoryId, CategoryModel.class);
        assertEquals(responseCategory.getId(), retrievedModel.getId(), "Category wasn't retrieved successfully");
    }

    @Test
    void testDeleteCategoryModel() throws Exception {

        CategoryModel order = createCategoryModel();
        CategoryModel responseCategory = postForObject(path, order, CategoryModel.class);
        final String categoryId = responseCategory.getId();
        runInTransaction(status -> {
            CategoryEntity categoryEntity = repository.findById(categoryId).orElseGet(() -> fail("Expected CategoryEntity not found"));

            assertEquals(categoryId, categoryEntity.getId(), "Category wasn't created successfully");
            return null;
        });

        MockHttpServletResponse response = deleteForObject(path, categoryId);
        assertEquals(200, response.getStatus(), "Category wasn't deleted successfully");
    }

    private CategoryModel createCategoryModel() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("category1");
        return categoryModel;
    }

}
