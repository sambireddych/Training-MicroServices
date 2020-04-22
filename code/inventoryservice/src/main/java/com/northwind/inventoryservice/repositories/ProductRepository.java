package com.northwind.inventoryservice.repositories;

import com.northwind.inventoryservice.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {

}
