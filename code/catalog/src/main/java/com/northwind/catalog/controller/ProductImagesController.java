package com.northwind.catalog.controller;


import com.northwind.catalog.REST_API;
import com.northwind.catalog.domain.Categories;
import com.northwind.catalog.domain.Product;
import com.northwind.catalog.domain.ProductImages;
import com.northwind.catalog.service.CategoriesService;
import com.northwind.catalog.service.ProductImageService;
import com.northwind.catalog.service.ProductService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = {REST_API.PRODUCT_IMAGES,REST_API.PRODUCT_IMAGES_CATEGORIES})
@Api(tags = "ProductImages")
public class ProductImagesController {

    private ProductService productService;
    private CategoriesService categoriesService;
    private ProductImageService productImageService;

    @Autowired
    public ProductImagesController(ProductService service, CategoriesService categoriesService, ProductImageService productImageService) {
        this.productService = service;
        this.categoriesService = categoriesService;
        this.productImageService = productImageService;
    }

    @ApiOperation(value = "List of ProductImages based on Product Id")
    @GetMapping(produces = "application/json")
    public Stream<List<ProductImages>> getAll(@PathVariable long categoryID, @PathVariable long productID) throws Exception {

        Maybe<Categories> byId = categoriesService.getById(categoryID);
        if(byId.isEmpty().blockingGet()){
            throw new Exception("Not Found");
        }
        List<Product> products = byId.blockingGet().getProducts().stream().collect(Collectors.toList());
        Stream<List<ProductImages>> listStream = products.stream().map(Product::getProductImages);

        return listStream;
    }

    @ApiOperation(value = "Retrieve the productimages by product id")
    @GetMapping(path = REST_API.PRODUCT_IMAGE_BY_ID)
    public Maybe<ProductImages> get(@PathVariable long id) {
        return productImageService.getById(id);
    }

    @ApiOperation(value = "saving the productimage based on product")
    @PostMapping
    public Observable<ProductImages> save(@PathVariable long productId, @RequestBody ProductImages productImages) throws Exception {

        Maybe<Product> productMaybe = productService.getById(productId);
        if (productMaybe.isEmpty().blockingGet()) {
            return new Observable<ProductImages>() {
                @Override
                protected void subscribeActual(Observer<? super ProductImages> observer) {
                    observer.onError(new Exception("Not Found"));
                }
            };
        } else {
            productImages.setProduct(productMaybe.blockingGet());
            return Observable.just(productImageService.save(productImages)).blockingFirst();
        }
    }

    @ApiOperation(value = "updating the productimage based on product")
    @PutMapping(path = REST_API.PRODUCT_IMAGE_BY_ID)
    public Observable<ProductImages> update(@PathVariable long productId, @RequestBody ProductImages productImages) throws Exception {

        Maybe<Product> productMaybe = productService.getById(productId);
        if (productMaybe.isEmpty().blockingGet()) {
            return new Observable<ProductImages>() {
                @Override
                protected void subscribeActual(Observer<? super ProductImages> observer) {
                    observer.onError(new Exception("Not Found"));
                }
            };
        } else {
            productImages.setProduct(productMaybe.blockingGet());
            return Observable.just(productImageService.save(productImages)).blockingFirst();
        }
    }


}
