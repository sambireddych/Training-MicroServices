package com.northwind.shippingservice.repositories;

import com.northwind.shippingservice.domain.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShippersRepository extends JpaRepository<Shippers,Long> {

    List<Shippers> findByCompanyName(String companyName);
}
