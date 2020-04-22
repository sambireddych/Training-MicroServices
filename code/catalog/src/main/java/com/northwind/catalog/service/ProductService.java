package com.northwind.catalog.service;

import com.northwind.catalog.domain.Product;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface ProductService {

    Observable<Iterable<Product>> getAll();
    Observable<Product> save(Product product);
    Maybe<Product> getById(long id);
    Observable<Product> update(Product product);
}
