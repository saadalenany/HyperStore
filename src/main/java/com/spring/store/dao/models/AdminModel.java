package com.spring.store.dao.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class AdminModel {

    private String id;

    private String name;

    private String phone;

    private String email;

    private LocalDateTime emailVerifiedAt;

    private String password;

    private List<ProductModel> products;
}
