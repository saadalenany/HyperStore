package com.spring.store.controllers;

import com.spring.store.dao.entities.AdminEntity;
import com.spring.store.dao.models.AdminModel;
import com.spring.store.dao.repos.AdminRepository;
import com.spring.store.utils.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AdminControllerTest extends BaseTest {

    private final String path = "/admin";

    @Autowired
    AdminRepository repository;

    @Test
    void testCreateAdminModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        AdminModel responseAdmin = postForObject(path, adminModel, AdminModel.class);

        final String adminId = responseAdmin.getId();
        runInTransaction(status -> {
            AdminEntity adminEntity = repository.findById(adminId).orElseGet(() -> fail("Expected AdminEntity not found"));

            assertEquals(adminId, adminEntity.getId(), "Admin wasn't created successfully");
            return null;
        });

    }

    @Test
    void testCreateAdminModelWithImage() throws Exception {

        AdminModel adminModel = createAdminModel();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("cool.jpg")).getFile());
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist...");
        }
        long fileLength = file.length();
        adminModel.setImage(readFileToByteArray(file));

        AdminModel responseAdmin = postForObject(path, adminModel, AdminModel.class);
        final String adminId = responseAdmin.getId();
        runInTransaction(status -> {
            AdminEntity adminEntity = repository.findById(adminId).orElseGet(() -> fail("Expected AdminEntity not found"));

            assertEquals(adminId, adminEntity.getId(), "Admin wasn't created successfully");
            assertEquals(fileLength, adminEntity.getImage().length, "Image wasn't created successfully");
            return null;
        });

    }

    @Test
    void testUpdateAdminModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        AdminModel responseAdmin = postForObject(path, adminModel, AdminModel.class);
        final String adminId = responseAdmin.getId();
        runInTransaction(status -> {
            AdminEntity adminEntity = repository.findById(adminId).orElseGet(() -> fail("Expected AdminEntity not found"));

            assertEquals(adminId, adminEntity.getId(), "Admin wasn't created successfully");
            return null;
        });

        responseAdmin.setName("test_name");
        responseAdmin = putForObject(path, responseAdmin, AdminModel.class);
        assertEquals("test_name", responseAdmin.getName(), "Name wasn't updated successfully");
    }

    @Test
    void testRetrieveAdminModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        AdminModel responseAdmin = postForObject(path, adminModel, AdminModel.class);
        final String adminId = responseAdmin.getId();
        runInTransaction(status -> {
            AdminEntity adminEntity = repository.findById(adminId).orElseGet(() -> fail("Expected AdminEntity not found"));

            assertEquals(adminId, adminEntity.getId(), "Admin wasn't created successfully");
            return null;
        });

        AdminModel retrievedModel = getForObject(path + "/" + adminId, AdminModel.class);
        assertEquals(responseAdmin.getId(), retrievedModel.getId(), "Admin wasn't retrieved successfully");
    }

    @Test
    void testDeleteAdminModel() throws Exception {

        AdminModel adminModel = createAdminModel();
        AdminModel responseAdmin = postForObject(path, adminModel, AdminModel.class);
        final String adminId = responseAdmin.getId();
        runInTransaction(status -> {
            AdminEntity adminEntity = repository.findById(adminId).orElseGet(() -> fail("Expected AdminEntity not found"));

            assertEquals(adminId, adminEntity.getId(), "Admin wasn't created successfully");
            return null;
        });

        MockHttpServletResponse response = deleteForObject(path, adminId);
        assertEquals(200, response.getStatus(), "Admin wasn't deleted successfully");
    }

    private AdminModel createAdminModel() {
        AdminModel adminModel = new AdminModel();
        adminModel.setName("admin1");
        adminModel.setPhone("+2012315455785");
        adminModel.setEmail("admin1@yahoo.com");
        adminModel.setPassword("hygtrfyedtwr");
        return adminModel;
    }

}
