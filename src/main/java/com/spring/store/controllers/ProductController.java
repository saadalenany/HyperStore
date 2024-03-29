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
        return ResponseEntity.ok(ProductService.findByAdmin(adminId));
    }

    @GetMapping("/by_category/{categoryId}")
    public ResponseEntity<List<ProductModel>> listByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(ProductService.findByCategory(categoryId));
    }

    @GetMapping("/by_category/{categoryId}/page")
    public ResponseEntity<List<ProductModel>> listByCategoryAsPage(@PathVariable String categoryId,
                                                                   @RequestParam(name = "page") Integer page,
                                                                   @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(ProductService.findByCategoryAsPage(categoryId, page, size));
    }

    @GetMapping("/by_category/{categoryId}/{name}/page")
    public ResponseEntity<List<ProductModel>> listByCategoryAndNameAsPage(@PathVariable String categoryId,
                                                                          @PathVariable String name,
                                                                          @RequestParam(name = "page") Integer page,
                                                                          @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(ProductService.findByCategoryAndNameAsPage(categoryId, name, page, size));
    }

    @GetMapping("/by_category/{categoryId}/{name}")
    public ResponseEntity<List<ProductModel>> listByCategoryAndName(@PathVariable String categoryId,
                                                                    @PathVariable String name) {
        return ResponseEntity.ok(ProductService.findByCategoryAndName(categoryId, name));
    }

    @GetMapping("/by_name/{name}/page")
    public ResponseEntity<List<ProductModel>> listByNameAsPage(@PathVariable String name,
                                                               @RequestParam(name = "page") Integer page,
                                                               @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(ProductService.findByNameAsPage(name, page, size));
    }

    @GetMapping("/by_name/{name}")
    public ResponseEntity<List<ProductModel>> listByName(@PathVariable String name) {
        return ResponseEntity.ok(ProductService.findByName(name));
    }

    @GetMapping("/by_date/{creationDate}")
    public ResponseEntity<List<ProductModel>> listByCreationDate(@PathVariable LocalDateTime creationDate) {
        return ResponseEntity.ok(ProductService.findByDate(creationDate));
    }

    @GetMapping("/by_rate/{rate}")
    public ResponseEntity<List<ProductModel>> listByRate(@PathVariable Integer rate) {
        return ResponseEntity.ok(ProductService.findByRate(rate));
    }

    @GetMapping("/by_rate/{rate}/page")
    public ResponseEntity<List<ProductModel>> listByRateAsPage(@PathVariable Integer rate,
                                                               @RequestParam(name = "page") Integer page,
                                                               @RequestParam(name = "size") Integer size,
                                                               @RequestParam(name = "exceptProduct") String exceptProduct) {
        return ResponseEntity.ok(ProductService.findByRateAsPage(rate, page, size, exceptProduct));
    }

    @GetMapping("/by_discount/{discount}")
    public ResponseEntity<List<ProductModel>> listByDiscount(@PathVariable Integer discount) {
        return ResponseEntity.ok(ProductService.findByDiscount(discount));
    }

    @GetMapping("/last_week")
    public ResponseEntity<List<ProductModel>> listByLastWeek() {
        return ResponseEntity.ok(ProductService.findByLastWeek(LocalDateTime.now()));
    }

    @GetMapping("/by_price/page")
    public ResponseEntity<List<ProductModel>> listByPriceAsPage(@RequestParam(name = "low") Integer low, @RequestParam(name = "high") Integer high, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(ProductService.findByPriceBetweenAsPage(low, high, page, size));
    }

    @GetMapping("/by_price")
    public ResponseEntity<List<ProductModel>> listByPrice(@RequestParam(name = "low") Integer low, @RequestParam(name = "high") Integer high) {
        return ResponseEntity.ok(ProductService.findByPriceBetween(low, high));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductModel>> list() {
        return ResponseEntity.ok(ProductService.findAll());
    }

    @GetMapping("/list/page")
    public ResponseEntity<List<ProductModel>> listAsPage(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(ProductService.findAllAsPage(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductModel> delete(@PathVariable String id) {
        return ResponseEntity.ok(ProductService.delete(id));
    }

}
