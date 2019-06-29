package com.spring.store.controllers;

import com.spring.store.dao.models.PaymentModel;
import com.spring.store.service.api.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentModel> post(@RequestBody PaymentModel model) {
        return ResponseEntity.ok(paymentService.save(model));
    }

    @PutMapping
    public ResponseEntity<PaymentModel> put(@RequestBody PaymentModel model) {
        return ResponseEntity.ok(paymentService.save(model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentModel> get(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentModel> delete(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.delete(id));
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<List<PaymentModel>> getByProduct(@PathVariable String product_id) {
        return ResponseEntity.ok(paymentService.getByProduct(product_id));
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<List<PaymentModel>> getByAdmin(@PathVariable(name = "admin") String admin) {
        return ResponseEntity.ok(paymentService.listByAdmin(admin));
    }

    @GetMapping("/{adminId}/page")
    public ResponseEntity<List<PaymentModel>> getByAdminAsPage(@PathVariable(name = "admin") String admin,
                                                               @RequestParam(name = "page") Integer page,
                                                               @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(paymentService.listByAdminAsPage(admin, page, size));
    }
}
