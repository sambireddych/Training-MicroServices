package com.northwind.shippingservice.service.impl;

import com.northwind.shippingservice.domain.ShippingRates;
import com.northwind.shippingservice.repositories.ShippingRatesRepository;
import com.northwind.shippingservice.service.ShippingRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingRatesServiceImpl implements ShippingRatesService {

    @Autowired
    private ShippingRatesRepository shippingRatesRepository;

    @Override
    public List<ShippingRates> getAll() {
        return shippingRatesRepository.findAll();
    }


    @Override
    public Optional<ShippingRates> getById(long id) {
        return shippingRatesRepository.findById(id);
    }

    @Override
    public ShippingRates save(ShippingRates shippingRates) {
        return shippingRatesRepository.saveAndFlush(shippingRates);
    }

    @Override
    public void delete(ShippingRates shippingRates) {
        shippingRatesRepository.delete(shippingRates);
    }

    @Override
    public BigDecimal calcuateRate(String country) {
        return shippingRatesRepository.findByCountry(country).get(0).getFlatRate();
    }
}
