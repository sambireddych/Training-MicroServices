package com.northwind.shippingservice.controllers;


import com.northwind.shippingservice.REST_API;
import com.northwind.shippingservice.domain.Shippers;
import com.northwind.shippingservice.domain.ShippingRates;
import com.northwind.shippingservice.service.ShippersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(REST_API.SHIPPERS)
public class ShippersController {


    @Autowired
    private ShippersService service;

    @GetMapping
    public ResponseEntity<List<Shippers>> get(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(params = REST_API.SHIPPERS_BY_COMPANYNAME)
    public ResponseEntity<List<Shippers>> getByCompanyName(@RequestParam String companyName){
        return new ResponseEntity<>(service.findByCompanyName(companyName),HttpStatus.OK);
    }

    @GetMapping(path = REST_API.SHIPPERS_BY_ID)
    public ResponseEntity<?> getById(@PathVariable long id){
        return new ResponseEntity<Shippers>(service.getById(id).get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Shippers shippers){
        long id = shippers.getId();
        Optional<Shippers> exist = service.getById(id);
        if(exist.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        Shippers savedShippingRates = service.save(shippers);
        return new ResponseEntity<>(savedShippingRates, HttpStatus.OK);
    }


    @DeleteMapping(path = REST_API.SHIPPINGRATES_BY_ID)
    public void delete(@PathVariable long id) throws Exception {
        Optional<Shippers> exist = service.getById(id);
        if(!exist.isPresent()){
            throw new Exception("Not founds");
        }
        service.delete(exist.get());
    }

    @PutMapping(path = REST_API.SHIPPINGRATES_BY_ID)
    public ResponseEntity<?> updateFlatRate(@PathVariable long id, @RequestBody Shippers shippers){
        Optional<Shippers> exist = service.getById(id);
        if(!exist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        exist.get().setCompanyName(shippers.getCompanyName());
        exist.get().setPhone(shippers.getPhone());
        return new ResponseEntity<>(service.save(exist.get()) ,HttpStatus.OK);
    }

}
