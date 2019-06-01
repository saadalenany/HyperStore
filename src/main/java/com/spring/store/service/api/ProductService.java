package com.spring.store.service.api;

import com.spring.store.dao.models.ProductModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {

    ProductModel save(ProductModel model);

    ProductModel get(String id);

    List<ProductModel> findByAdmin(String adminId);

    List<ProductModel> findByCategory(String category_id);

    List<ProductModel> findByDate(LocalDateTime creationDate);

    List<ProductModel> findByLastWeek(LocalDateTime creationDate);

    List<ProductModel> findByRate(Integer rate);

    List<ProductModel> findByDiscount(Integer discount);

    ProductModel delete(String id);

}
