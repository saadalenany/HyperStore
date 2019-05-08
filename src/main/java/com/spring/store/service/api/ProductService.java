package com.spring.store.service.api;

import com.spring.store.dao.models.ProductModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {

    ProductModel save(ProductModel model);

    ProductModel get(String id);

    List<ProductModel> listByAdmin(String adminId);

    List<ProductModel> listByCategory(String category_id);

    List<ProductModel> listByDate(LocalDateTime creationDate);

    List<ProductModel> listByLastWeek(LocalDateTime creationDate);

    ProductModel delete(String id);

}
