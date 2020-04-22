package com.northwind.catalog.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Proxy;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonSerialize
@Entity
@Table(name = "Categories")
@Proxy(lazy=false)
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String categoryName;
    private String description;
    private byte[] picture;
    @Version
    private long version;
    private UUID objectId;
    private boolean inStock;




    @OneToMany(mappedBy = "categories",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();



    public Categories() {
        this.objectId = UUID.randomUUID();
    }


    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }
}
