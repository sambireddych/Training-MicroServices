package com.northwind.catalog.repository;


import com.northwind.catalog.domain.Categories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Categories,Long> {
}
