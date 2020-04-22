package com.northwind.catalog.repository;

import com.northwind.catalog.domain.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages,Long> {
}
