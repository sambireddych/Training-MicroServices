package com.northwind.shippingservice.workers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JsonSerialize
public class ShippingEvent implements Serializable {
    @JsonProperty
    private String eventType;
    @JsonProperty
    private Map<String, Object> data = new HashMap<>();

    @JsonProperty
    private Set<OrderDetailsEvent> orderDetails= new HashSet();

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Set<OrderDetailsEvent> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetailsEvent> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
