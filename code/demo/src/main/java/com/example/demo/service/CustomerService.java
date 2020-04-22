package com.example.demo.service;

import com.example.demo.domain.Customer;
import io.reactivex.Observable;

import java.util.List;


public interface CustomerService {

    Customer getCustomer(final long id);
    List<Customer> getAll();
}
