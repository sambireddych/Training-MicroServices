package com.northwind.shippingservice.service;

import com.northwind.shippingservice.domain.PackingSlipDetails;
import com.northwind.shippingservice.domain.PackingSlips;

import java.util.List;
import java.util.Optional;

public interface PackingSlipservice {

    List<PackingSlips> findByShipName(String shipName);
    List<PackingSlips> getAll() throws Exception;
    Optional<PackingSlips> getById(long id);
    PackingSlips getOrderById(String orderId);
    PackingSlips save(PackingSlips packingSlips);
    List<PackingSlips> getByShipCountry(String shipCountry);
    List<PackingSlips> getByPostalCode(String postalCode);
    void delete(long id);
}
