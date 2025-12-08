package com.angel.curso.springboot.app.validation;

import com.angel.curso.springboot.app.services.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class IsExistDbValidation implements ConstraintValidator<IsExistDb, String> {

    private final ProductService service;

    public IsExistDbValidation(ProductService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !service.existsBySku(value);
    }

}
