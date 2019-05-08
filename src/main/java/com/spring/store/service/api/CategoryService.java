package com.spring.store.service.api;

import com.spring.store.dao.models.CategoryModel;

import java.util.List;

public interface CategoryService {

    CategoryModel save(CategoryModel model);

    CategoryModel get(String id);

    List<CategoryModel> list();

    CategoryModel delete(String id);

}
