package com.northwind.shippingservice.service;

import com.northwind.shippingservice.domain.ShippingRates;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ShippingRatesService {

    List<ShippingRates> getAll();
    Optional<ShippingRates> getById(long id);
    ShippingRates save(ShippingRates shippingRates);
    void delete(ShippingRates shippingRates);
    BigDecimal calcuateRate(String country);
}
