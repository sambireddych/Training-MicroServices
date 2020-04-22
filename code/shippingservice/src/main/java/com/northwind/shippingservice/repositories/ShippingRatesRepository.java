package com.northwind.shippingservice.repositories;


import com.northwind.shippingservice.domain.Shippers;
import com.northwind.shippingservice.domain.ShippingRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRatesRepository extends JpaRepository<ShippingRates,Long> {
    List<ShippingRates> findByCountry (String country);

}
