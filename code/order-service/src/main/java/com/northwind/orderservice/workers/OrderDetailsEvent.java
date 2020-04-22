package com.northwind.orderservice.workers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
public class OrderDetailsEvent {

    @JsonProperty
    private String eventType;
    @JsonProperty
    private Map<String,Object> orderDetails= new HashMap<>();
    public Map<String, Object> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(Map<String, Object> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
