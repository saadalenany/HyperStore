package com.spring.store.controllers;

import com.spring.store.dao.entities.ProductEntity;
import com.spring.store.dao.models.AdminModel;
import com.spring.store.dao.models.CategoryModel;
import com.spring.store.dao.models.ProductModel;
import com.spring.store.dao.repos.ProductRepository;
import com.spring.store.utils.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ProductControllerTest extends BaseTest {

    private final String productPath = "/product";

    private final String categoryPath = "/category";

    private final String adminPath = "/admin";

    @Autowired
    ProductRepository repository;

    @Test
    void testCreateProductModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        adminModel = postForObject(adminPath, adminModel, AdminModel.class);

        CategoryModel categoryModel = createCategoryModel();
        categoryModel = postForObject(categoryPath, categoryModel, CategoryModel.class);

        ProductModel productModel = createProductModel();
        productModel.setAdminId(adminModel.getId());
        productModel.setCategoryId(categoryModel.getId());

        productModel = postForObject(productPath, productModel, ProductModel.class);
        final String productId = productModel.getId();
        runInTransaction(status -> {
            ProductEntity productEntity = repository.findById(productId).orElseGet(() -> fail("Expected ProductEntity not found"));

            assertEquals(productId, productEntity.getId(), "Product wasn't created successfully");
            return null;
        });

    }

    @Test
    void testCreateProductModelWithImage() throws Exception {

        AdminModel adminModel = createAdminModel();
        adminModel = postForObject(adminPath, adminModel, AdminModel.class);

        CategoryModel categoryModel = createCategoryModel();
        categoryModel = postForObject(categoryPath, categoryModel, CategoryModel.class);

        ProductModel productModel = createProductModel();
        productModel.setAdminId(adminModel.getId());
        productModel.setCategoryId(categoryModel.getId());
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("cool.jpg")).getFile());
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist...");
        }
        long fileLength = file.length();
        productModel.setImage(readFileToByteArray(file));

        productModel = postForObject(productPath, productModel, ProductModel.class);
        final String productId = productModel.getId();
        runInTransaction(status -> {
            ProductEntity productEntity = repository.findById(productId).orElseGet(() -> fail("Expected ProductEntity not found"));

            assertEquals(productId, productEntity.getId(), "Product wasn't created successfully");
            assertEquals(fileLength, productEntity.getImage().length, "Image wasn't created successfully");
            return null;
        });

    }

    @Test
    void testUpdateProductModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        adminModel = postForObject(adminPath, adminModel, AdminModel.class);

        CategoryModel categoryModel = createCategoryModel();
        categoryModel = postForObject(categoryPath, categoryModel, CategoryModel.class);

        ProductModel productModel = createProductModel();
        productModel.setAdminId(adminModel.getId());
        productModel.setCategoryId(categoryModel.getId());

        productModel = postForObject(productPath, productModel, ProductModel.class);
        final String productId = productModel.getId();
        runInTransaction(status -> {
            ProductEntity productEntity = repository.findById(productId).orElseGet(() -> fail("Expected ProductEntity not found"));

            assertEquals(productId, productEntity.getId(), "Product wasn't created successfully");
            return null;
        });

        productModel.setName("test_product");
        productModel = putForObject(productPath, productModel, ProductModel.class);
        assertEquals("test_product", productModel.getName(), "Product Name wasn't updated successfully");
    }

    @Test
    void testRetrieveProductModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        adminModel = postForObject(adminPath, adminModel, AdminModel.class);

        CategoryModel categoryModel = createCategoryModel();
        categoryModel = postForObject(categoryPath, categoryModel, CategoryModel.class);

        ProductModel productModel = createProductModel();
        productModel.setAdminId(adminModel.getId());
        productModel.setCategoryId(categoryModel.getId());

        productModel = postForObject(productPath, productModel, ProductModel.class);
        final String productId = productModel.getId();
        runInTransaction(status -> {
            ProductEntity productEntity = repository.findById(productId).orElseGet(() -> fail("Expected ProductEntity not found"));

            assertEquals(productId, productEntity.getId(), "Product wasn't created successfully");
            return null;
        });

        ProductModel retrievedProduct = getForObject(productPath + "/" + productId, ProductModel.class);
        assertEquals(productModel.getId(), retrievedProduct.getId(), "Product wasn't retrieved successfully");
    }

    @Test
    void testDeleteProductModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        adminModel = postForObject(adminPath, adminModel, AdminModel.class);

        CategoryModel categoryModel = createCategoryModel();
        categoryModel = postForObject(categoryPath, categoryModel, CategoryModel.class);

        ProductModel productModel = createProductModel();
        productModel.setAdminId(adminModel.getId());
        productModel.setCategoryId(categoryModel.getId());

        productModel = postForObject(productPath, productModel, ProductModel.class);
        final String productId = productModel.getId();
        runInTransaction(status -> {
            ProductEntity productEntity = repository.findById(productId).orElseGet(() -> fail("Expected ProductEntity not found"));

            assertEquals(productId, productEntity.getId(), "Product wasn't created successfully");
            return null;
        });

        MockHttpServletResponse response = deleteForObject(productPath, productId);
        assertEquals(200, response.getStatus(), "Product wasn't deleted successfully");
    }

    private ProductModel createProductModel() {
        ProductModel productModel = new ProductModel();
        productModel.setName("product1");
        productModel.setDiscountPrice(0);
        productModel.setQuantity(10);
        productModel.setPrice(1520);
        return productModel;
    }

    private AdminModel createAdminModel() {
        AdminModel adminModel = new AdminModel();
        adminModel.setName("admin1");
        adminModel.setPhone("+2012315455785");
        adminModel.setEmail("admin1@yahoo.com");
        adminModel.setPassword("hygtrfyedtwr");
        return adminModel;
    }

    private CategoryModel createCategoryModel() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("category1");
        return categoryModel;
    }

}
