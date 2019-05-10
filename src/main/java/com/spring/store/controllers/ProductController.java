package com.spring.store.controllers;

import com.spring.store.dao.models.ProductModel;
import com.spring.store.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService ProductService;

    @Autowired
    public ProductController(ProductService ProductService) {
        this.ProductService = ProductService;
    }

    @PostMapping
    public ResponseEntity<ProductModel> post(@RequestBody ProductModel model) {
        return ResponseEntity.ok(ProductService.save(model));
    }

    @PutMapping
    public ResponseEntity<ProductModel> put(@RequestBody ProductModel model) {
        return ResponseEntity.ok(ProductService.save(model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> get(@PathVariable String id) {
        return ResponseEntity.ok(ProductService.get(id));
    }

    @GetMapping("/by_admin/{adminId}")
    public ResponseEntity<List<ProductModel>> listByAdmin(@PathVariable String adminId) {
        return ResponseEntity.ok(ProductService.listByAdmin(adminId));
    }

    @GetMapping("/by_category/{categoryId}")
    public ResponseEntity<List<ProductModel>> listByCategoryId(@PathVariable String categoryId) {
        return ResponseEntity.ok(ProductService.listByCategory(categoryId));
    }

    @GetMapping("/by_date/{creationDate}")
    public ResponseEntity<List<ProductModel>> listByCreationDate(@PathVariable LocalDateTime creationDate) {
        return ResponseEntity.ok(ProductService.listByDate(creationDate));
    }

    @GetMapping("/last_week")
    public ResponseEntity<List<ProductModel>> listByLastWeek() {
        return ResponseEntity.ok(ProductService.listByLastWeek(LocalDateTime.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductModel> delete(@PathVariable String id) {
        return ResponseEntity.ok(ProductService.delete(id));
    }

}
