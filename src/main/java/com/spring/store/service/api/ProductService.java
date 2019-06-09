package com.spring.store.service.api;

import com.spring.store.dao.models.ProductModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {

    ProductModel save(ProductModel model);

    ProductModel get(String id);

    List<ProductModel> findByAdmin(String adminId);

    List<ProductModel> findByCategory(String category_id);

    List<ProductModel> findByCategoryAsPage(String category_id, Integer page, Integer size);

    List<ProductModel> findByDate(LocalDateTime creationDate);

    List<ProductModel> findByLastWeek(LocalDateTime creationDate);

    List<ProductModel> findByRate(Integer rate);

    List<ProductModel> findByDiscount(Integer discount);

    List<ProductModel> findByPriceBetweenAsPage(Integer low, Integer high, Integer page, Integer size);

    List<ProductModel> findByPriceBetween(Integer low, Integer high);

    void delete(String id);

    Integer getProductCount();

    List<ProductModel> findAll();

    List<ProductModel> findAllAsPage(Integer page, Integer size);

    List<ProductModel> findByRateAsPage(Integer rate, Integer page, Integer size, String exceptProduct);

}
