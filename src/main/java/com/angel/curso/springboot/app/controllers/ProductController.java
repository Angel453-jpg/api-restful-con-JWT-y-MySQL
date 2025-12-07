package com.angel.curso.springboot.app.controllers;

import com.angel.curso.springboot.app.config.ProductValidation;
import com.angel.curso.springboot.app.entities.Product;
import com.angel.curso.springboot.app.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private final ProductValidation validation;

    public ProductController(ProductService service, ProductValidation validation) {
        this.service = service;
        this.validation = validation;
    }

    @GetMapping
    public List<Product> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok().body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {

        validation.validate(product, result);

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {

        validation.validate(product, result);

        Optional<Product> productOptional = service.update(id, product);

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        if (productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<Product> productOptional = service.delete(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok().body(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
