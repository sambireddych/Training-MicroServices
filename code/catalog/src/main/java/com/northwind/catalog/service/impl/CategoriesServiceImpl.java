package com.northwind.catalog.service.impl;

import com.northwind.catalog.domain.Categories;
import com.northwind.catalog.repository.CategoryRepository;
import com.northwind.catalog.service.CategoriesService;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CategoriesServiceImpl implements CategoriesService {

    private CategoryRepository repository;

    @Autowired
    public CategoriesServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Iterable<Categories>> getAll() {
        return Observable.fromCallable(()-> {
            Iterable<Categories> es = repository.findAll();
            return es;
        });
    }

    @Override
    public Observable<Categories> save(Categories categories) {
//        return Single.create(emitter -> repository.save(categories));
        return Observable.fromCallable(() -> repository.save(categories));
    }

    @Override
    public Maybe<Categories> getById(long id) {
        return Maybe.fromCallable(() -> {
             return repository.findById(id).orElseThrow(() -> new Exception("not found"));
                }).doOnError(Throwable::getMessage);
//                repository.findById(id).get())
//                .doOnError(throwable -> {throw new Exception("Not found");})
//                .doOnSuccess(categories -> repository.findById(id));
    }

    @Override
    public void delete(Categories categories) {
        repository.delete(categories);
    }

}
