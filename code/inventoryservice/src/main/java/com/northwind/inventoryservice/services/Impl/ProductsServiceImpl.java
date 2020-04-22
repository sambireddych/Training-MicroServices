package com.northwind.inventoryservice.services.Impl;

import com.northwind.inventoryservice.domain.Products;
import com.northwind.inventoryservice.repositories.ProductRepository;
import com.northwind.inventoryservice.repositories.SuppliersRepository;
import com.northwind.inventoryservice.services.ProductsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    private ProductRepository repository;

    public ProductsServiceImpl(ProductRepository repository, SuppliersRepository suppliersRepository) {
        this.repository = repository;
    }

    public List<Products> getAll() {

        return repository.findAll();
    }

    public Optional<Products> get(long id) {
        return repository.findById(id);
    }

    public Products save(Products product) {
        return repository.saveAndFlush(product);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
