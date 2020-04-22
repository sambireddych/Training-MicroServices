package com.northwind.catalog;

public interface REST_API {

    //Catrgories End-points
    public static final String CATAGORIES = "/categories";
    public static final String CATAGORIES_BY_ID = "/{id}";

    public static final String PRODUCTS =  "/categories/{categoryId}/products";
    public static final String PRODUCTS_BY_ID = "/{id}";

    public static final String PRODUCT_IMAGES = "/product/{productID}/productImages";

    public static final String PRODUCT_IMAGES_CATEGORIES ="/categories/{categoryID}/product/{productID}/productImages";

    public static final String PRODUCT_IMAGE_BY_ID = "/{id}";
}
