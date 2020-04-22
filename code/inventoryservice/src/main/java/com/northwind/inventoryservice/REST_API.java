package com.northwind.inventoryservice;


public interface REST_API {

    String SUPPLIERS = "/suppliers";
    String SUPPLIERS_BY_ID="/{id}";

    String PRODUCTS = "/suppliers/{supplierID}/products";
    String PRODUCTS_BY_ID = "/{id}";
}
