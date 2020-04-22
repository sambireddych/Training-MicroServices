package com.example.demo.service;

import com.example.demo.client.CustomerClient;
import com.example.demo.domain.Customer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerClient customerClient;

    @Override
    public Customer getCustomer(long id) {
        return customerClient.find(id).subscribeOn(Schedulers.io()).blockingGet();
    }

    @Override
    public List<Customer> getAll() {
        return customerClient.getAll()
                .flatMap(id -> {
                    return customerClient.find(id).toObservable();
                }).toList().blockingGet();
    }
}
