package com.northwind.shippingservice.repositories;

import com.northwind.shippingservice.domain.PackingSlipDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackingSlipDetailsRepository  extends JpaRepository<PackingSlipDetails,Long> {
}
