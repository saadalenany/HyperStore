package com.spring.store.controllers;

import com.spring.store.dao.models.CategoryModel;
import com.spring.store.service.api.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryModel> post(@RequestBody CategoryModel model) {
        return ResponseEntity.ok(categoryService.save(model));
    }

    @PutMapping
    public ResponseEntity<CategoryModel> put(@RequestBody CategoryModel model) {
        return ResponseEntity.ok(categoryService.save(model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryModel> get(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryModel>> list() {
        return ResponseEntity.ok(categoryService.list());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryModel> delete(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

}
