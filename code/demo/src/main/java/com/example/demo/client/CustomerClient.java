package com.example.demo.client;

import com.example.demo.domain.Customer;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface CustomerClient {

    Single<Customer> find(final long id);
    Observable<Long> getAll();
}
