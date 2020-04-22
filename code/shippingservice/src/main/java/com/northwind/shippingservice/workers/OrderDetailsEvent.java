package com.northwind.shippingservice.workers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsEvent {
    @JsonProperty
    private Map<String,String> orderDetails= new HashMap<>();
    public Map<String, String> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(Map<String, String> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
