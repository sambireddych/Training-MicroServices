package com.northwind.inventoryservice.rabbitMQ;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.northwind.inventoryservice.domain.Products;
import com.northwind.inventoryservice.services.ProductsService;
import com.northwind.inventoryservice.services.SuppliersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

@Component
public class RabbitMQWorker {

    private SuppliersService suppliersService;
    private RabbitTemplate rabbit;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ProductsService productsService;
    private Log logger;


    @Autowired
    public RabbitMQWorker(SuppliersService suppliersService, RabbitTemplate rabbit, ProductsService productsService) {
        this.suppliersService = suppliersService;
        this.rabbit = rabbit;
        this.productsService = productsService;
        this.logger = LogFactory.getLog(RabbitMQWorker.class);
    }

    @RabbitListener(queues = "category-service-inventory-service")
    public void checkCreateOrUpdate(String message) throws Exception {
        ProductEvent event = objectMapper.readValue(message, ProductEvent.class);

        if (event.getEventType().equals("PRODUCT_CREATED")) {
            productCreated(event);
        } else if (event.getEventType().equals("PRODUCT_UPDATED")) {
            productUpdated(event);
        }
    }

    public void productUpdated(ProductEvent event) throws Exception {
        Optional<Products> products = productsService.get(Long.parseLong(event.getId()));
        products.get().setDiscontinued(false);
        products.get().setLocation("NJ");
        products.get().setProductName(event.getProductName());
        products.get().setQuantityPerUnit(event.getQuantityPerUnit());
        products.get().setUnitPrice(new BigDecimal(event.getUnitPrice()));
        productsService.save(products.get());
    }

    public void productCreated(ProductEvent event) throws Exception {
        Products products = new Products();
        products.setProductName(event.getProductName());
        products.setQuantityPerUnit(event.getQuantityPerUnit());
        products.setUnitPrice(new BigDecimal(event.getUnitPrice()));
        productsService.save(products);

    }

    public void productsCheckInStock(long productID) throws Exception {
        Optional<Products> products = productsService.get(productID);
        UnitInStock inStock = new UnitInStock();
        inStock.setId(String.valueOf(products.get().getId()));
        inStock.setUnitInStock(String.valueOf(products.get().getUnitsInStock()));
        String json = "";
        try {
            json = objectMapper.writeValueAsString(inStock);
        } catch (JsonProcessingException jp) {
            throw new Exception(jp.getMessage());
        }
        try {
            rabbit.convertAndSend("inventory-service", "", json);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }


}
