package com.example.demo.client;

import com.example.demo.domain.Customer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CustomerClientImpl implements CustomerClient {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Single<Customer> find(long id) {
        return Single.create(singleSubsriber -> singleSubsriber.onSuccess(
                customerRepository.find(id)
        ));
    }

    @Override
    public Observable<Long> getAll() {
        return Observable.<Long>unsafeCreate(sub ->{
            customerRepository.findAll().forEach(id -> sub.onNext(id));
        }).zipWith(Observable.interval(1,TimeUnit.SECONDS),(id,along)->id);
    }
}
