package com.northwind.shippingservice.domain;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "PackingSlips")
public class PackingSlips implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PackingSlipID")
    private long id;
    @Column(name = "ShipName",length = 60)
    @NotNull
    @NotEmpty
    private String shipName;
    @Column(name = "ShipAddress",length = 60)
    @NotNull
    @NotEmpty
    private String shipAddress;
    @Column(name = "ShipCity",length = 30)
    @NotNull
    @NotEmpty
    private String shipCity;
    @Column(name = "ShipRegion",length = 24)
    private String shipRegion;
    @Column(name = "ShipPostalCode",length = 24)
    @NotNull
    @NotEmpty
    private String shipPostalCode;
    @Column(name = "ShipCountry",length = 24)
    @NotNull
    @NotEmpty
    private String shipCountry;
    @Column(name = "OrderID")
    @NotNull
    private int orderID;
    @Version
    private long version;
    @Column(name = "ObjectID")
    private UUID objectId;

    @OneToMany(mappedBy = "packingSlips", cascade = {CascadeType.PERSIST,CascadeType.ALL}, orphanRemoval = true)
    @JsonManagedReference
    private Set<PackingSlipDetails> addItems;



    public PackingSlips() {
        this.objectId = UUID.randomUUID();
    }

    public PackingSlips(String shipName){
        this.shipName = shipName;
        this.objectId = UUID.randomUUID();
    }


    public long getVersion() {
        return version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Set<PackingSlipDetails> getAddItems() {
        return addItems;
    }


    public void addItem(PackingSlipDetails details){

        addItems.add(details);
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

    public void setAddItems(Set<PackingSlipDetails> addItems) {
        this.addItems = addItems;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


}
