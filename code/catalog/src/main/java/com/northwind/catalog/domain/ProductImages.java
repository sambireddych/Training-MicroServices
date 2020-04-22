package com.northwind.catalog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.UUID;

@JsonSerialize
@Entity
@Table(name = "ProductImages")
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductImageID")
    private long id;
    @Column(name = "imageUri")
    private String imageURI;
    @Version
    private int version;
    @Column(name = "objectID")
    private UUID objectId;

    @JsonIgnore
    @Transient
    @MapsId
    private long productID;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    @JsonBackReference
    private Product product;

    public ProductImages() {
        this.objectId = UUID.randomUUID();
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }
}
