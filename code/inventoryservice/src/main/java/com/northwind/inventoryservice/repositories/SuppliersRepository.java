package com.northwind.inventoryservice.repositories;

import com.northwind.inventoryservice.domain.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers,Long> {
}
