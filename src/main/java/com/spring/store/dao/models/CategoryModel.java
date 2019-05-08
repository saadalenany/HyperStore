package com.spring.store.dao.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryModel {

    private String id;

    private String name;

    private List<ProductModel> products;
}
