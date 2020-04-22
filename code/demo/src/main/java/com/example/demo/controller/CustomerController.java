package com.example.demo.controller;


import com.example.demo.client.CustomerRepository;
import com.example.demo.domain.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;



    @GetMapping
    public ResponseEntity<List<Customer>> get(){
        return new ResponseEntity<>(customerService.getAll(),HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> get(@PathVariable long id){
        return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.CREATED);
    }

}
