package com.northwind.inventoryservice.controllers;


import com.northwind.inventoryservice.REST_API;
import com.northwind.inventoryservice.domain.Products;
import com.northwind.inventoryservice.domain.Suppliers;
import com.northwind.inventoryservice.services.SuppliersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = REST_API.SUPPLIERS, produces = "application/json")
public class SuppliersController {


    private SuppliersService service;

    public SuppliersController(SuppliersService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Suppliers>> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(path = REST_API.SUPPLIERS_BY_ID)
    public ResponseEntity<Suppliers> get(@PathVariable long id) {

        Optional<Suppliers> suppliers = service.get(id);
        if (!suppliers.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(suppliers.get());
    }

    @PostMapping
    public ResponseEntity<Suppliers> create(@RequestBody Suppliers model) {
        Suppliers suppliers = service.save(model);
        return ResponseEntity.created(URI.create(String.format("/products/%s", suppliers.getId())))
                .body(suppliers);
    }

    @PutMapping(path = REST_API.SUPPLIERS_BY_ID)
    public ResponseEntity<Suppliers> update(@PathVariable long id, @RequestBody Products model) {
        Optional<Suppliers> suppliers = service.get(id);
        if (!suppliers.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (suppliers.get().getVersion() > model.getVersion()) {
            return new ResponseEntity<>(suppliers.get(), HttpStatus.CONFLICT);
        }
        Suppliers savedSuppliers = service.save(suppliers.get());

        return ResponseEntity.ok(savedSuppliers);
    }

    @DeleteMapping(path = REST_API.SUPPLIERS_BY_ID)
    public ResponseEntity delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
