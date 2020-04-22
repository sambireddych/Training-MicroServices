package com.northwind.inventoryservice.controllers;


import com.northwind.inventoryservice.REST_API;
import com.northwind.inventoryservice.domain.Products;
import com.northwind.inventoryservice.domain.Suppliers;
import com.northwind.inventoryservice.services.ProductsService;
import com.northwind.inventoryservice.services.SuppliersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = REST_API.PRODUCTS, produces = "application/json")
public class ProductController {
    private ProductsService service;
    private SuppliersService suppliersService;

    public ProductController(ProductsService service, SuppliersService suppliersService) {
        this.service = service;
        this.suppliersService = suppliersService;
    }

    @GetMapping
    public ResponseEntity<List<Products>> getAll(@PathVariable long supplierID) {
        Optional<Suppliers> suppliers = suppliersService.get(supplierID);
        List<Products> collect = suppliers.get().getProductsList().stream().collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    @GetMapping(path = REST_API.PRODUCTS_BY_ID)
    public ResponseEntity<Products> get(@PathVariable long id) {

        Optional<Products> product = service.get(id);
        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }

    @PostMapping
    public ResponseEntity<Products> create(@RequestBody Products model) {
        Products product = service.save(model);
        return ResponseEntity.created(URI.create(String.format("/products/%s", product.getId())))
                .body(product);
    }

    @PutMapping(path = REST_API.PRODUCTS_BY_ID)
    public ResponseEntity<Products> update(@PathVariable long id, @RequestBody Products model) {
        Optional<Products> product = service.get(id);
        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (product.get().getVersion() > model.getVersion()) {
            return new ResponseEntity<>(product.get(), HttpStatus.CONFLICT);
        }
        Products savedProduct = service.save(product.get());

        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping(REST_API.PRODUCTS_BY_ID)
    public ResponseEntity delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
