package com.northwind.orderservice.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonSerialize
public class MonthComparisionModel {

    @JsonProperty
    private String month;
    @JsonProperty
    private BigDecimal totalAmount;

    public MonthComparisionModel() {

    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
