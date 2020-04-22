package com.northwind.catalog.service.impl;

import com.northwind.catalog.domain.ProductImages;
import com.northwind.catalog.repository.ProductImagesRepository;
import com.northwind.catalog.service.ProductImageService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductImagesServiceImpl implements ProductImageService {

    private ProductImagesRepository repository;

    @Autowired
    public ProductImagesServiceImpl(ProductImagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Iterable<ProductImages>> getAll() {
        return Observable.fromCallable(()-> {
            Iterable<ProductImages> es = repository.findAll();
            return es;
        }).subscribeOn(Schedulers.newThread()).doOnError(throwable -> new Exception("Server Error"));
//                subscribeOn(Schedulers.computation()).doOnError(throwable -> new Exception("Server Error"));
    }

    @Override
    public Observable<ProductImages> save(ProductImages productImages) {
        return Observable.fromCallable(() -> repository.save(productImages));
    }

    @Override
    public Maybe<ProductImages> getById(long id) {
        return Maybe.fromCallable(() -> {
            return repository.findById(id).orElseThrow(() -> new Exception("not found"));
        }).doOnError(Throwable::getMessage);
//                repository.findById(id).get())
//                .doOnError(throwable -> {throw new Exception("Not found");})
//                .doOnSuccess(categories -> repository.findById(id));
    }

    @Override
    public Observable<ProductImages> update(ProductImages productImages) {

        return null;
    }

}
