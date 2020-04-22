package com.northwind.shippingservice.controllers;

import com.northwind.shippingservice.REST_API;
import com.northwind.shippingservice.domain.PackingSlips;
import com.northwind.shippingservice.domain.Shippers;
import com.northwind.shippingservice.domain.ShippingRates;
import com.northwind.shippingservice.service.ShippingRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(REST_API.SHIPPINGRATES)
public class ShipperRatesController {

    @Autowired
    private ShippingRatesService service;

    @GetMapping
    public ResponseEntity<List<ShippingRates>> get(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(params = REST_API.SHIPPINGRATES_BY_COUNTRY)
    public ResponseEntity<?> getByCountry(@RequestParam String country){
        return new ResponseEntity<>(service.calcuateRate(country),HttpStatus.OK);
    }

    @GetMapping(path = REST_API.SHIPPINGRATES_BY_ID)
    public ResponseEntity<?> getById(@PathVariable long id){
        return new ResponseEntity<ShippingRates>(service.getById(id).get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ShippingRates shippingRates){
        long id = shippingRates.getId();
        Optional<ShippingRates> exist = service.getById(id);
        if(exist.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        ShippingRates savedShippingRates = service.save(shippingRates);
        return new ResponseEntity<>(savedShippingRates, HttpStatus.OK);
    }

    /*@GetMapping(params = REST_API.SHIPPINGRATE_BY_COUNTRY_FLATRATE)
    public ResponseEntity<BigDecimal> get(@RequestParam String country) {
        return new ResponseEntity<>(service.calcuateRate(country),HttpStatus.OK);
    }*/

    @DeleteMapping(path = REST_API.SHIPPINGRATES_BY_ID)
    public void delete(@PathVariable long id) throws Exception {
        Optional<ShippingRates> exist = service.getById(id);
        if(!exist.isPresent()){
            throw new Exception("Not found");
        }
        service.delete(exist.get());
    }

    @PutMapping(path = REST_API.SHIPPINGRATES_BY_ID)
    public ResponseEntity<?> updateFlatRate(@PathVariable long id, @RequestBody ShippingRates shippingRates){
        Optional<ShippingRates> exist = service.getById(id);
        if(!exist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        exist.get().setFlatRate(shippingRates.getFlatRate());
        exist.get().setCountry(shippingRates.getCountry());
        return new ResponseEntity<>(service.save(exist.get()) ,HttpStatus.OK);
    }



}
