package com.northwind.shippingservice.service.impl;

import com.northwind.shippingservice.domain.Shippers;
import com.northwind.shippingservice.repositories.ShippersRepository;
import com.northwind.shippingservice.service.ShippersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippersService {

    @Autowired
    private ShippersRepository shippersRepository;

    @Override
    public List<Shippers> findByCompanyName(String companyName) {
        return shippersRepository.findByCompanyName(companyName);
    }

    @Override
    public List<Shippers> getAll() {
        return shippersRepository.findAll();
    }

    @Override
    public Optional<Shippers> getById(long id) {
        return shippersRepository.findById(id);
    }

    @Override
    public Shippers save(Shippers shippers) {
        return shippersRepository.saveAndFlush(shippers);
    }

    @Override
    public void delete(Shippers shippers) {
        shippersRepository.delete(shippers);
    }
}
