package com.northwind.inventoryservice.services;

import com.northwind.inventoryservice.domain.Products;
import com.northwind.inventoryservice.domain.Suppliers;

import java.util.List;
import java.util.Optional;

public interface SuppliersService {

    List<Suppliers> getAll();
    Optional<Suppliers> get(long id);
    Suppliers save(Suppliers suppliers);
    void delete(long id);
}
