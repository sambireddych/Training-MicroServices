package com.example.demo.client;


import com.example.demo.domain.Customer;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {

    private static final List<Customer> customerSource = new ArrayList<>();
    static {
        customerSource.add(new Customer(1L,"sambi","chanimella"));
        customerSource.add(new Customer(2L,"Hana","Godfrey"));
        customerSource.add(new Customer(3L,"sambi Reddy","chanimella"));
    }

    Customer find(final long id) throws Exception {
        return customerSource.stream().filter(customer -> customer.getId() == id)
                .findFirst().orElseThrow(NotFound::new);
    }

    List<Long> findAll(){return customerSource.stream().map(Customer::getId).collect(Collectors.toList());}

}
