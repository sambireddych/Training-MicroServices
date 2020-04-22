package com.northwind.inventoryservice.services;

import com.northwind.inventoryservice.domain.Products;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<Products> getAll();
    Optional<Products> get(long id);
    Products save(Products product);
    void delete(long id);
}
