package com.northwind.inventoryservice.rabbitMQ;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class UnitInStock {


    @JsonProperty
    private String id;
    @JsonProperty
    private String unitInStock;

    public UnitInStock() {
    }

    public String getUnitInStock() {
        return unitInStock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUnitInStock(String unitInStock) {
        this.unitInStock = unitInStock;
    }
}
