package com.northwind.shippingservice.service.impl;

import com.northwind.shippingservice.domain.PackingSlipDetails;
import com.northwind.shippingservice.repositories.PackingSlipDetailsRepository;
import com.northwind.shippingservice.repositories.PackingSlipsRepository;
import com.northwind.shippingservice.service.PackingSlipDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PackingSlipDetailsServiceImpl implements PackingSlipDetailsService {


    @Autowired
    PackingSlipDetailsRepository packingSlipDetailsRepository;

    @Autowired
    PackingSlipsRepository packingSlipsRepository;


    @Override
    public Optional<PackingSlipDetails> findById(Long id) {
        return packingSlipDetailsRepository.findById(id);
    }

    @Override
    public List<PackingSlipDetails> getAll() {
        return packingSlipDetailsRepository.findAll();
    }

    @Override
    public PackingSlipDetails save(PackingSlipDetails packingSlipDetails) {
        return packingSlipDetailsRepository.saveAndFlush(packingSlipDetails);
    }
}
