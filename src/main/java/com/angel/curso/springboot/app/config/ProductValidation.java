package com.angel.curso.springboot.app.config;

import com.angel.curso.springboot.app.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "es requerido!");
        if (!StringUtils.hasText(product.getName())) {
            errors.rejectValue("description", "", "es requerido, por favor");
        }
        if (product.getPrice() == null) {
            errors.rejectValue("price", "", "no puede ser nulo, ok!");
        } else if (product.getPrice() < 500) {
            errors.rejectValue("price", "", "debe ser un valor numérico mayor o igual que 500!");
        }
    }

}
