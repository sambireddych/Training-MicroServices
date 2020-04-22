package com.northwind.shippingservice.domain;


import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ShippingRates")

public class ShippingRates {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ShippingRateID")
    private long id;

    @Column(name = "Country")
    private String country;

    @Column(name = "FlatRate")
    private BigDecimal flatRate;
    @Version
    private long veriosn;


    public ShippingRates(){
        this.veriosn =1;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(BigDecimal flatRate) {
        this.flatRate = flatRate;
    }

    public long getVeriosn() {
        return veriosn;
    }

    public void setVeriosn(long veriosn) {
        this.veriosn = veriosn;
    }
}
