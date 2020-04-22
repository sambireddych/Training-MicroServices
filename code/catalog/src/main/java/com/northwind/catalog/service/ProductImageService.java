package com.northwind.catalog.service;

import com.northwind.catalog.domain.ProductImages;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface ProductImageService {
    Observable<Iterable<ProductImages>> getAll();
    Observable<ProductImages> save(ProductImages productImages);
    Maybe<ProductImages> getById(long id);
    Observable<ProductImages> update(ProductImages product);
}
