package com.spring.store.controllers;

import com.spring.store.dao.entities.AdminEntity;
import com.spring.store.dao.models.AdminModel;
import com.spring.store.dao.repos.AdminRepository;
import com.spring.store.utils.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AdminControllerTest extends BaseTest {

    private final String path = "/admin";

    @Autowired
    AdminRepository repository;

    @Test
    void test001() throws Exception {

        AdminModel order = createOrderObject();
        AdminModel responseAdmin = postForObject(path, order, AdminModel.class);

        final String adminId = responseAdmin.getId();
        runInTransaction(status -> {
            AdminEntity adminEntity = repository.findById(adminId).orElseGet(() -> fail("Expected AdminEntity not found"));

            assertEquals(adminId, adminEntity.getId(), "Admin wasn't created successfully");
            return null;
        });

    }

    private AdminModel createOrderObject() {
        AdminModel adminModel = new AdminModel();
        adminModel.setName("admin1");
        adminModel.setPhone("+2012315455785");
        adminModel.setEmail("admin1@yahoo.com");
        adminModel.setPassword("hygtrfyedtwr");
        return adminModel;
    }

}
