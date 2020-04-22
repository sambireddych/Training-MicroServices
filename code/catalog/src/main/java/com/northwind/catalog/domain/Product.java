package com.northwind.catalog.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonSerialize
@Entity
@Table(name = "Product")
@Proxy(lazy=false)

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private long id;

    private String productName;
    private String quantiyPerUnit;
    private boolean isPublished;
    private BigDecimal listPrice;
    @Version
    private long version;
    private UUID objectId;


    private boolean inStock;


    @JsonIgnore
    @Transient
    @MapsId
    private long categoryId;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    @JsonBackReference
    private Categories categories;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.ALL},orphanRemoval = true)
    @JsonManagedReference
    private List<ProductImages> productImages = new ArrayList<>();

    public Product() {
        this.objectId = UUID.randomUUID();
    }

    public long getId() {
        return id;
    }


    public Categories getCategories() {
        return categories;
    }


    public boolean isInStock() {
        return inStock;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(ProductImages productImages) {
        this.productImages.add(productImages);
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    public void setCategories(Categories categories) {
        this.categories = categories;
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

    public String getQuantiyPerUnit() {
        return quantiyPerUnit;
    }

    public void setQuantiyPerUnit(String quantiyPerUnit) {
        this.quantiyPerUnit = quantiyPerUnit;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
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
