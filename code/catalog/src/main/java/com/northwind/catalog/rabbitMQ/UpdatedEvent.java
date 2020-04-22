package com.northwind.catalog.rabbitMQ;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatedEvent extends Event {
    @JsonProperty
    String productName;
    @JsonProperty
    String QuantityPerUnit;
    @JsonProperty
    String unitPrice;

    public UpdatedEvent() {
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
