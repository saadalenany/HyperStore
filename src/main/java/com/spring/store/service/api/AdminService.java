package com.spring.store.service.api;

import com.spring.store.dao.models.AdminModel;

import java.util.List;

public interface AdminService {

    AdminModel save(AdminModel model);

    AdminModel get(String id);

    List<AdminModel> list();

    AdminModel delete(String id);

    AdminModel getByEmailAndPassword(String email, String password);

    AdminModel getByUsername(String username);
}
