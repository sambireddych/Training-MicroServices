package com.northwind.inventoryservice.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name ="Products")
public class Products {

    @Id
    @GeneratedValue
    @Column(name = "ProductID")
    private long id;

    @Column(name = "ProductName",length = 40)
    @NotNull
    @NotEmpty
    private String productName;

    @Column(name = "QuantityPerUnit")
    private String quantityPerUnit;

    @Column(name = "UnitPrice")
    private BigDecimal unitPrice;

    @Column(name = "UnitsOnOrder")
    private int unitsOnOrder;
    @Column(name = "UnitsInStock")
    private int unitsInStock;
    @Column(name = "ReorderLevel")
    private int reorderLevel;
    @Column(name = "Discontinued")
    private boolean discontinued;
    @Column(name = "Location")
    private String location;
    @Version
    private int version;

    @Column(name = "ObjectID")
    private UUID objectID;

    @JsonIgnore
    @Transient
    @MapsId
    private long supplierID;

    @ManyToOne
    @JoinColumn(name = "SupplierID")
    @JsonBackReference
    private Suppliers suppliers;
    public Products() {
        this.objectID = UUID.randomUUID();
        this.version =1;
        this.discontinued = false;
    }

    public long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(long supplierID) {
        this.supplierID = supplierID;
    }

    public long getId() {
        return id;
    }

    public Suppliers getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Suppliers suppliers) {
        this.suppliers = suppliers;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(int unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public UUID getObjectID() {
        return objectID;
    }

    public void setObjectID(UUID objectID) {
        this.objectID = objectID;
    }
}
