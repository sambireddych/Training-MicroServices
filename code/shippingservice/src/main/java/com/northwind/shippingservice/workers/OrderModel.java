package com.northwind.shippingservice.workers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@JsonSerialize
public class OrderModel {
    @JsonProperty
    private long orderId;
    @JsonProperty
    private long customerId;
    @JsonProperty
    private String customerNo;
    @JsonProperty
    private String companyName;
    @JsonProperty
    private Date orderDate;
    @JsonProperty
    private BigDecimal total;
    @JsonProperty
    private Date shippedDate;
    @JsonProperty
    private String shipName;
    @JsonProperty
    private String shipAddress;
    @JsonProperty
    private String shipCity;
    @JsonProperty
    private String shipRegion;
    @JsonProperty
    private String shipPostalCode;
    @JsonProperty
    private String shipCountry;
    @JsonProperty
    private OrderStatus status;
    @JsonProperty
    private long version;


    @JsonProperty
    private Set<OrderItemModel> orderItemModelSet = new HashSet<>();
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCompanyName() {
        return companyName;
    }


    public Set<OrderItemModel> getOrderItemModelSet() {
        return orderItemModelSet;
    }

    public void setOrderItemModelSet(Set<OrderItemModel> orderItemModelSet) {
        this.orderItemModelSet = orderItemModelSet;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipRegion() {
        return shipRegion;
    }

    public void setShipRegion(String shipRegion) {
        this.shipRegion = shipRegion;
    }

    public String getShipPostalCode() {
        return shipPostalCode;
    }

    public void setShipPostalCode(String shipPostalCode) {
        this.shipPostalCode = shipPostalCode;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }
}
