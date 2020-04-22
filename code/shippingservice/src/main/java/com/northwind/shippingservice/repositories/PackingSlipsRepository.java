package com.northwind.shippingservice.repositories;

import com.northwind.shippingservice.domain.PackingSlipDetails;
import com.northwind.shippingservice.domain.PackingSlips;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface PackingSlipsRepository extends JpaRepository<PackingSlips, Long>{

    List<PackingSlips> findByShipName (String shipName);
    List<PackingSlips> findByShipPostalCode (String shipPostalCode);
    PackingSlips getByOrderID (String orderID);

    List<PackingSlips> findByShipCountry(String shipCountry);


    PackingSlipDetails save(PackingSlipDetails packingSlipDetails);

}
