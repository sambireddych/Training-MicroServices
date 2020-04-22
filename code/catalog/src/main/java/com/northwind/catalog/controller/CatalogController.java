package com.northwind.catalog.controller;


import com.northwind.catalog.REST_API;
import com.northwind.catalog.config.Secured;
import com.northwind.catalog.domain.Categories;
import com.northwind.catalog.service.CategoriesService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@Api(tags = "Categories")
@RequestMapping(value = REST_API.CATAGORIES,produces = "application/json")
public class CatalogController {
    private CategoriesService service;

    @Autowired
    public CatalogController(CategoriesService service) {
        this.service = service;
    }

    @ApiOperation(value = "List of Categories")
    @GetMapping(produces = "application/json")
//    @Secured(value = "read")
    public Observable<Iterable<Categories>> getAll() throws Exception {
        Observable<Iterable<Categories>> caIterableObservable = null;

        try {
            caIterableObservable = service.getAll();
        } catch (Exception ie) {
            throw new Exception(ie.getMessage());
        }
        return caIterableObservable;
    }

    @ApiOperation(value = "Retrieve the category and products by category id")
    @GetMapping(path = REST_API.CATAGORIES_BY_ID)
    public Maybe<Categories> get(@PathVariable long id) throws Exception {
        Maybe<Categories> categoriesMaybe = null;
        try {
            categoriesMaybe = service.getById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return categoriesMaybe;
    }

//    @Secured(value = "write")
    @ApiOperation(value = "saving the category and if possible we can send the product payload here")
    @PostMapping
    public Observable<Categories> save(@RequestBody Categories categories) throws Exception {
        Observable<Categories> categoriesObservable = null;

        try {
            categoriesObservable = service.save(categories);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return categoriesObservable;
    }

    @ApiOperation(value = "Deleting the row using id")
    @DeleteMapping(path = REST_API.CATAGORIES_BY_ID)
    public void delete(@PathVariable long id) {
        Maybe<Categories> category = service.getById(id);
        if (category.isEmpty().blockingGet()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        service.delete(category.blockingGet());
    }


    @ApiOperation(value = "Updating the catagory using catagory id")
    @PutMapping(path = REST_API.CATAGORIES_BY_ID)
    public Observable<Categories> update(@PathVariable long id, @RequestBody Categories categories) {
        Maybe<Categories> categoriesMaybe = service.getById(id);
        if (categoriesMaybe.isEmpty().blockingGet()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        categoriesMaybe.blockingGet().setCategoryName(categories.getCategoryName());
        categoriesMaybe.blockingGet().setDescription(categories.getDescription());
        return service.save(categoriesMaybe.blockingGet());
    }
}
