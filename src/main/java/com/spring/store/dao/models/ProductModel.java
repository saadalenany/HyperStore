package com.spring.store.dao.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProductModel {

    private String id;

    private String categoryId;

    private String adminId;

    private String name;

    private Integer price;

    private byte[] image;

    private LocalDateTime creationDate;

    private Integer discountPrice;

    private Integer quantity;

}
