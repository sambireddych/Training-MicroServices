package com.northwind.inventoryservice.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Suppliers")
public class Suppliers {


    @Id
    @GeneratedValue
    @Column(name = "SupplierID")
    private long id;

    @Column(name = "CompanyName")
    private String companyName;
    @Column(name = "ContactName")
    private String contactName;
    @Column(name = "ContactTitle")
    private String contactTitle;
    @Column(name = "Address")
    private String address;

    @Column(name="City")
    private String city;
    @Column(name="Region")
    private String region;
    @Column(name="PostalCode")
    private String postalCOde;
    @Column(name="Country")
    private String country;
    @Column(name="Phone")
    private String phone;
    @Column(name="Fax")
    private String fax;
    @Column(name="HomePage")
    private String homePage;
    @Version
    private int version;
    @Column(name = "ObjectID")
    private UUID objectID;


    @OneToMany(mappedBy = "suppliers",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    @JsonManagedReference
    private List<Products> productsList = new ArrayList<>();

    public Suppliers() {
        this.objectID = UUID.randomUUID();
        this.version =1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(Products products) {
        this.productsList.add(products);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCOde() {
        return postalCOde;
    }

    public void setPostalCOde(String postalCOde) {
        this.postalCOde = postalCOde;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
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
