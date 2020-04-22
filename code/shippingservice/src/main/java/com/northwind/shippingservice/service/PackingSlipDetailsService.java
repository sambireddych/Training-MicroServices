package com.northwind.shippingservice.service;

import com.northwind.shippingservice.domain.PackingSlipDetails;

import java.util.List;
import java.util.Optional;

public interface PackingSlipDetailsService {

    Optional<PackingSlipDetails> findById(Long id);
    List<PackingSlipDetails> getAll();

    PackingSlipDetails save(PackingSlipDetails packingSlipDetails);

}
