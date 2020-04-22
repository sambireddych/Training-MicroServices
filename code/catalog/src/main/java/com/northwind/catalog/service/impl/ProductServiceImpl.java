package com.northwind.catalog.service.impl;

import com.northwind.catalog.domain.Product;
import com.northwind.catalog.repository.ProductRepository;
import com.northwind.catalog.service.ProductService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ProductServiceImpl implements ProductService {


    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Iterable<Product>> getAll() {
        return Observable.fromCallable(()-> {
            Iterable<Product> es = repository.findAll();
            return es;
        }).subscribeOn(Schedulers.newThread()).doOnError(throwable -> new Exception("Server Error"));
//                subscribeOn(Schedulers.computation()).doOnError(throwable -> new Exception("Server Error"));
    }

    @Override
    public Observable<Product> save(Product product) {
        return Observable.fromCallable(() -> repository.save(product));
    }

    @Override
    public Maybe<Product> getById(long id) {
        return Maybe.fromCallable(() -> {
            return repository.findById(id).orElseThrow(() -> new Exception("not found"));
        }).doOnError(Throwable::getMessage);
//                repository.findById(id).get())
//                .doOnError(throwable -> {throw new Exception("Not found");})
//                .doOnSuccess(categories -> repository.findById(id));
    }

    @Override
    public Observable<Product> update(Product product) {
        return null;
    }
}
