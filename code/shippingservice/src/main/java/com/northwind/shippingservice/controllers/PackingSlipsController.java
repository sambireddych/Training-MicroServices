package com.northwind.shippingservice.controllers;


import com.northwind.shippingservice.REST_API;
import com.northwind.shippingservice.domain.PackingSlips;
import com.northwind.shippingservice.service.PackingSlipservice;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(REST_API.PACKINGSLIPS)
@Timed
@Api(tags = "PackingSlips")
public class PackingSlipsController {

    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private PackingSlipservice packingSlipservice;





    @GetMapping(produces = "application/json")
    @ApiOperation(value = "List of PackingSLips", notes="This operations supports paging with limit and offset.")
    public ResponseEntity<List<PackingSlips>> get(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> limit) throws Exception {
        List<PackingSlips> packingSlips = packingSlipservice.getAll();
        return new ResponseEntity<>(packingSlips, HttpStatus.CREATED);
    }

    @GetMapping(path = REST_API.PACKINGSLIP_BY_ID,produces = "application/json")
    @ApiOperation(value="Retrieve a packingSlipItem by ID")

    public ResponseEntity<PackingSlips> getID(@PathVariable long id) {

        Optional<PackingSlips> packingSlip = packingSlipservice.getById(id);
        if (!packingSlip.isPresent()) {

            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(packingSlip.get(), HttpStatus.OK);
    }

    @GetMapping(params = REST_API.PACKINGSLIP_BY_SHIPNAME,produces = "application/json")
    @ApiOperation(value="Retrieve a list of PackingSlipItem by shipName")
    public ResponseEntity<List<PackingSlips>> findByShipName(@RequestParam String shipName) {
        return new ResponseEntity<>(packingSlipservice.findByShipName(shipName), HttpStatus.OK);
    }

    @GetMapping(params = REST_API.PACKINGSLIP_BY_ORDERID)
    public PackingSlips getByOrderID(@RequestParam String orderID) {
        PackingSlips packingSlip = packingSlipservice.getOrderById(orderID);

        if (packingSlip == null) {
        }

        return packingSlip;
    }

    @GetMapping(params = REST_API.PACKINGSLIP_BY_SHIPPOSTALCODE, produces = "application/json")
    @ApiOperation(value="Retrieve a list of PackingSlipItem by postalcode")
    public ResponseEntity<List<PackingSlips>> getByShipPostalCode(@RequestParam String shipPostalCode) {
        return (ResponseEntity<List<PackingSlips>>) packingSlipservice.getByPostalCode(shipPostalCode);
    }

    @GetMapping(params = REST_API.PACKINGSLIP_BY_SHIPCOUNTRY,produces = "application/json")
    @ApiOperation(value="Retrieve a list of PackingSlipItem by country")
    public ResponseEntity<List<PackingSlips>> getByShipCountry(@RequestParam String shipCountry) {
        return (ResponseEntity<List<PackingSlips>>) packingSlipservice.getByShipCountry(shipCountry);
    }

    @PostMapping(produces = "application/json",consumes = "application/json")
    @ApiOperation(value = "saving the packing slip data by sending in json format")
    public ResponseEntity<?> create(@RequestBody PackingSlips model) {

        long id = model.getId();

        Optional<PackingSlips> checkPackingSlips = packingSlipservice.getById(id);
        if (checkPackingSlips.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        PackingSlips savedPackingSlip = packingSlipservice.save(model);
        return ResponseEntity.created(URI.create("/pakingslips/" + savedPackingSlip.getId()))
                .body(savedPackingSlip);
    }

    @PutMapping(path = REST_API.PACKINGSLIP_BY_ID)
    public ResponseEntity<PackingSlips> update(@PathVariable long id,
                                                   @RequestBody PackingSlips model) throws Exception {
        PackingSlips existingPackingSlip = packingSlipservice.getById(id).orElseThrow(Exception::new );
        PackingSlips merged = toEntity(existingPackingSlip,model);
        return new ResponseEntity<>(packingSlipservice.save(merged) ,HttpStatus.OK);
    }

    @DeleteMapping(REST_API.PACKINGSLIP_BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) throws Exception {
        Optional<PackingSlips> existingPackingSlip = packingSlipservice.getById(id);
        if (!existingPackingSlip.isPresent()) {
            return;
        }
        packingSlipservice.delete(id);
    }


    private static PackingSlips toEntity(PackingSlips model, PackingSlips entity) {
        entity.setShipName(model.getShipName());
        entity.setShipCountry(model.getShipCountry());
        entity.setShipCity(model.getShipCity());
        entity.setShipPostalCode(model.getShipPostalCode());
        entity.setShipRegion(model.getShipRegion());
        entity.setShipAddress(model.getShipAddress());
        return entity;
    }

}
