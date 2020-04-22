package com.northwind.inventoryservice.services.Impl;


import com.northwind.inventoryservice.domain.Products;
import com.northwind.inventoryservice.domain.Suppliers;
import com.northwind.inventoryservice.repositories.ProductRepository;
import com.northwind.inventoryservice.repositories.SuppliersRepository;
import com.northwind.inventoryservice.services.SuppliersService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    private SuppliersRepository repository;

    public SuppliersServiceImpl(SuppliersRepository repository) {
        this.repository = repository;
    }

    public List<Suppliers> getAll() {

        return repository.findAll();
    }

    public Optional<Suppliers> get(long id) {
        return repository.findById(id);
    }

    public Suppliers save(Suppliers suppliers) {
        return repository.saveAndFlush(suppliers);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
