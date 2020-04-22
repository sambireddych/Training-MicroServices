package com.northwind.shippingservice.service;

import com.northwind.shippingservice.domain.Shippers;

import java.util.List;
import java.util.Optional;

public interface ShippersService {

    List<Shippers> findByCompanyName(String companyName);
    List<Shippers> getAll();
    Optional<Shippers> getById(long id);

    Shippers save(Shippers shippers);
    void delete(Shippers shippers);
}
