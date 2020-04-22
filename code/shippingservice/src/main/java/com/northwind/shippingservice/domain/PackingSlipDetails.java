package com.northwind.shippingservice.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PackingSlipDetails")
public class PackingSlipDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PackingSlipDetailsID")
    private long id;
    @Column(name = "ProductName")
    private String productName;
    @Column(name = "Quantity")
    private int quantity;
    @Version
    private int version;

    @JsonIgnore
    @Transient
    @MapsId
    private long packingSlipID;

    @ManyToOne
    @JoinColumn(name = "PackingSlipID")
    @JsonBackReference
    private PackingSlips packingSlips;

    public PackingSlipDetails(){
        this.version =1;
    }

    public PackingSlipDetails(long packingSlipID, PackingSlips packingSlips){
        this.packingSlips = packingSlips;
        this.id = packingSlipID;

    }

    public PackingSlips getPackingSlips() {
        return packingSlips;
    }

    public void setPackingSlips(PackingSlips packingSlips) {
        this.packingSlips = packingSlips;
    }

    public long getPackingSlipID() {
        return packingSlipID;
    }

    public void setPackingSlipID(long packingSlipID) {
        this.packingSlipID = packingSlipID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {

        if (productName == null)
            throw new IllegalArgumentException("productName is required.");

        String cleanProductName = productName.trim();
        if (cleanProductName.length() == 0 || cleanProductName.length() > 60)
            throw new IllegalArgumentException("ProductName must be between 1 and 60 characters.");

        this.productName = cleanProductName;
    }




    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {

        if (quantity == 0)
            throw new IllegalArgumentException("quantity is required.");

        if(quantity!=0 && quantity < 30){
            this.quantity = quantity;
        }else{
            throw new IllegalArgumentException("quantity should be between 1 to 30");
        }
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        if (version == 0)
            throw new IllegalArgumentException("Version cannot be zero.");

        if (version < this.version)
            throw new IllegalArgumentException("Version cannot be older than the current version.");

        this.version = version;
    }


}
