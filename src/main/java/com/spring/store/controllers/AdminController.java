package com.spring.store.controllers;

import com.spring.store.dao.models.AdminModel;
import com.spring.store.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminModel> post(@RequestBody AdminModel model) {
        return ResponseEntity.ok(adminService.save(model));
    }

    @PutMapping
    public ResponseEntity<AdminModel> put(@RequestBody AdminModel model) {
        return ResponseEntity.ok(adminService.save(model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminModel> get(@PathVariable String id) {
        return ResponseEntity.ok(adminService.get(id));
    }

    @GetMapping("/by_email_pass")
    public ResponseEntity<AdminModel> getByEmailAndPassword(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        return ResponseEntity.ok(adminService.getByEmailAndPassword(email, password));
    }

    @GetMapping("/by_name_pass")
    public ResponseEntity<AdminModel> getByUsernameAndPassword(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password) {
        return ResponseEntity.ok(adminService.getByUsernameAndPassword(name, password));
    }

    @GetMapping("/by_name")
    public ResponseEntity<AdminModel> getByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(adminService.getByUsername(name));
    }

    @GetMapping
    public ResponseEntity<List<AdminModel>> list() {
        return ResponseEntity.ok(adminService.list());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdminModel> delete(@PathVariable String id) {
        return ResponseEntity.ok(adminService.delete(id));
    }

}
