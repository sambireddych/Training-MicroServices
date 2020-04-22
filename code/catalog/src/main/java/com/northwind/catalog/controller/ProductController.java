package com.northwind.catalog.controller;


import com.northwind.catalog.REST_API;
import com.northwind.catalog.domain.Categories;
import com.northwind.catalog.domain.Product;
import com.northwind.catalog.rabbitMQ.RabbitMQWorker;
import com.northwind.catalog.service.CategoriesService;
import com.northwind.catalog.service.ProductService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = REST_API.PRODUCTS,produces = "application/json")
@Api(tags = "Products")
public class ProductController {


    private RabbitMQWorker rabbitMQWorker;
    private ProductService productService;
    private CategoriesService categoriesService;

    @Autowired
    public ProductController(RabbitMQWorker rabbitMQWorker, ProductService service, CategoriesService categoriesService) {
        this.rabbitMQWorker = rabbitMQWorker;
        this.productService = service;
        this.categoriesService = categoriesService;
    }

    @ApiOperation(value = "List of Categories")
    @GetMapping(produces = "application/json")
    public Observable<Product> getAll(@PathVariable long categoryId) throws Exception {
        Maybe<Categories> byId = categoriesService.getById(categoryId);
        if(byId.isEmpty().blockingGet()){
            throw new Exception("Not Found");
        }
        List<Product> products = byId.blockingGet().getProducts().stream().collect(Collectors.toList());
        return Observable.fromIterable(products);
    }

    @ApiOperation(value = "Retrieve the products by product id")
    @GetMapping(path = REST_API.PRODUCTS_BY_ID)
    public Maybe<Product> get(@PathVariable long id) {
        return productService.getById(id);
    }

    @ApiOperation(value = "saving the product based on category")
    @PostMapping
    public Observable<Product> save(@PathVariable long categoryId, @RequestBody Product product) throws Exception {

        Maybe<Categories> categories = categoriesService.getById(categoryId);
        if (categories.isEmpty().blockingGet()) {
            return new Observable<Product>() {
                @Override
                protected void subscribeActual(Observer<? super Product> observer) {
                    observer.onError(new Exception("Not Found"));
                }
            };
        } else {
            product.setCategories(categories.blockingGet());
            return Observable.just(productService.save(product)).blockingFirst().doOnComplete(() -> rabbitMQWorker.productCreated(categoryId));
        }
    }

    @ApiOperation(value = "updating the product based on category")
    @PutMapping(path = REST_API.PRODUCTS_BY_ID)
    public Observable<Product> update(@PathVariable long categoryId, @RequestBody Product product) throws Exception {

        Maybe<Categories> categories = categoriesService.getById(categoryId);
        if (categories.isEmpty().blockingGet()) {
            return new Observable<Product>() {
                @Override
                protected void subscribeActual(Observer<? super Product> observer) {
                    observer.onError(new Exception("Not Found"));
                }
            };
        } else {
            product.setCategories(categories.blockingGet());
            return Observable.just(productService.save(product)).blockingFirst()
                    .doOnComplete(() -> rabbitMQWorker.productUpdated(categoryId))
                    .doOnError(Throwable::getMessage);
        }
    }
}
