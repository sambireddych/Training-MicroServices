package com.northwind.catalog.service;

import com.northwind.catalog.domain.Categories;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public interface CategoriesService {

    Observable<Iterable<Categories>> getAll();
    Observable<Categories> save(Categories categories);
    Maybe<Categories> getById(long id);
    void delete(Categories categories);
}
