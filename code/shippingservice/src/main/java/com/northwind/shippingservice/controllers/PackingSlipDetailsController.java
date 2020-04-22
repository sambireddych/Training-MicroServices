package com.northwind.shippingservice.controllers;


import com.northwind.shippingservice.REST_API;
import com.northwind.shippingservice.domain.PackingSlipDetails;
import com.northwind.shippingservice.domain.PackingSlips;
import com.northwind.shippingservice.service.PackingSlipDetailsService;
import com.northwind.shippingservice.service.PackingSlipservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(REST_API.PACKINGSLIPDETAILS)
public class PackingSlipDetailsController {


    @Autowired
    PackingSlipservice packingSlipservice ;
    @Autowired
    PackingSlipDetailsService packingSlipDetailsService;

    @GetMapping
    public ResponseEntity<List<PackingSlipDetails>> get(@PathVariable long packingSlipID) {

        Optional<PackingSlips> packingSlip = packingSlipservice.getById(packingSlipID);
        List<PackingSlipDetails> details = packingSlip.get().getAddItems().stream().collect(Collectors.toList());
        return ResponseEntity.ok().body(details);
    }

    @GetMapping(REST_API.PACKINGSLIPDETAILS_BY_ID)
    public ResponseEntity<PackingSlipDetails> get(@PathVariable long packingSlipID, @PathVariable long id) {

        Optional<PackingSlips> packingSlip = packingSlipservice.getById(packingSlipID);
        PackingSlipDetails packingSlipDetails =  packingSlip.get().getAddItems().stream().filter(details -> details.getId()==id).findFirst().get();
        return ResponseEntity.ok().body(packingSlipDetails);
    }

    @PostMapping
    public ResponseEntity<?> create(@PathVariable long packingSlipID,
                                          @RequestBody PackingSlipDetails model) {

        Optional<PackingSlips> packingSlips = packingSlipservice.getById(packingSlipID);
        if(!packingSlips.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        model.setPackingSlips(packingSlips.get());
        PackingSlipDetails savedPackingSlipDetails = packingSlipDetailsService.save(model);
        return new ResponseEntity<>(savedPackingSlipDetails,HttpStatus.OK);
    }


}
