package com.northwind.shippingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northwind.shippingservice.domain.PackingSlips;
import com.northwind.shippingservice.repositories.PackingSlipDetailsRepository;
import com.northwind.shippingservice.repositories.PackingSlipsRepository;
import com.northwind.shippingservice.service.PackingSlipservice;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackingSlipServiceImpl implements PackingSlipservice {


    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private RabbitTemplate rabbit;

    @Autowired
    private PackingSlipsRepository packingSlipsRepository;

    @Autowired
    private PackingSlipDetailsRepository packingSlipDetailsRepository;

    @Override
    public List<PackingSlips> findByShipName(String shipName) {
        return  packingSlipsRepository.findByShipName(shipName);
    }

    @Override
    public List<PackingSlips> getAll() throws Exception {
       List<PackingSlips> packingSlips = packingSlipsRepository.findAll();
       try {
           rabbit.convertAndSend("logs", "", mapper.writeValueAsString(packingSlips.get(0)));
           System.out.println(rabbit.receive("shipping-events", 10000));
       }catch (Exception e){
           throw new Exception(e.getMessage());
       }

        return packingSlips;
    }

    @Override
    public Optional<PackingSlips> getById(long id) {
        return packingSlipsRepository.findById(id);
    }

    @Override
    public PackingSlips getOrderById(String orderId) {
        return packingSlipsRepository.getByOrderID(orderId);
    }

    @Override
    public PackingSlips save(PackingSlips packingSlips) {
        return packingSlipsRepository.saveAndFlush(packingSlips);
    }

    @Override
    public List<PackingSlips> getByShipCountry(String shipCountry) {
        return  packingSlipsRepository.findByShipCountry(shipCountry);
    }

    @Override
    public List<PackingSlips> getByPostalCode(String postalCode) {
        return  packingSlipsRepository.findByShipPostalCode(postalCode);
    }




    @Override
    public void delete(long id) {
        packingSlipsRepository.deleteById(id);
    }
}
