package com.northwind.orderservice.adapters;

import java.math.BigDecimal;

public class ShippingRateModel {
    private BigDecimal freight;

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }
}
