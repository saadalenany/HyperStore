package com.spring.store.controllers;

import com.spring.store.dao.models.RatesModel;
import com.spring.store.service.api.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rates")
public class RatesController {

    private final RatesService ratesService;

    @Autowired
    public RatesController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @PostMapping
    public ResponseEntity<RatesModel> post(@RequestBody RatesModel model) {
        return ResponseEntity.ok(ratesService.save(model));
    }

    @PutMapping
    public ResponseEntity<RatesModel> put(@RequestBody RatesModel model) {
        return ResponseEntity.ok(ratesService.save(model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatesModel> get(@PathVariable String id) {
        return ResponseEntity.ok(ratesService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RatesModel> delete(@PathVariable String id) {
        return ResponseEntity.ok(ratesService.delete(id));
    }

}
