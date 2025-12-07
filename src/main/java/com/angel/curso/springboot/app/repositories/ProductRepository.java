package com.angel.curso.springboot.app.repositories;

import com.angel.curso.springboot.app.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {


}
