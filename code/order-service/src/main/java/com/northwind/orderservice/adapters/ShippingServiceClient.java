package com.northwind.orderservice.adapters;

import java.math.BigDecimal;

public interface ShippingServiceClient {
    BigDecimal getFreightAmount(String country);
}
