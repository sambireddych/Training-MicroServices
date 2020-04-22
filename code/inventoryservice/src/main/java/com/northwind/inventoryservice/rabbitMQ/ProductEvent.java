package com.northwind.inventoryservice.rabbitMQ;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ProductEvent {


    @JsonProperty
    private String eventType;
    @JsonProperty
    private String id;
    @JsonProperty
    private String productName;
    @JsonProperty
    private String QuantityPerUnit;
    @JsonProperty
    private String unitPrice;

    public ProductEvent() {
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantityPerUnit() {
        return QuantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        QuantityPerUnit = quantityPerUnit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
