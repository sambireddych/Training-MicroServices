package com.northwind.catalog.rabbitMQ;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.northwind.catalog.domain.Product;
import com.northwind.catalog.service.CategoriesService;
import com.northwind.catalog.service.ProductService;
import io.reactivex.Maybe;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQWorker {

    private CategoriesService categoriesService;
    private RabbitTemplate rabbit;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ProductService productService;

    @Autowired
    public RabbitMQWorker(CategoriesService categoriesService, RabbitTemplate rabbit, ProductService productService) {
        this.categoriesService = categoriesService;
        this.rabbit = rabbit;
        this.productService = productService;
    }


    public void productCreated(long id) throws Exception {

        CreatedEvent event = new CreatedEvent();
        Maybe<Product> productMaybe = productService.getById(id);
        event.setEventType(EventType.PRODUCT_CREATED);
        event.setId(Long.toString(productMaybe.blockingGet().getId()));
        event.setProductName(productMaybe.blockingGet().getProductName());
        event.setQuantityPerUnit(productMaybe.blockingGet().getQuantiyPerUnit());
        event.setUnitPrice(productMaybe.blockingGet().getListPrice().toString());
        String json = "";
        try {
            json = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException jp) {
            throw new Exception(jp.getMessage());
        }
        try {
            rabbit.convertAndSend("category-events", "", json);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void productUpdated(long id) throws Exception {

        Maybe<Product> productMaybe = productService.getById(id);
        UpdatedEvent event = new UpdatedEvent();
        event.setEventType(EventType.PRODUCT_UPDATED);
        event.setId(Long.toString(productMaybe.blockingGet().getId()));
        event.setProductName(productMaybe.blockingGet().getProductName());
        event.setQuantityPerUnit(productMaybe.blockingGet().getQuantiyPerUnit());
        event.setUnitPrice(productMaybe.blockingGet().getListPrice().toString());
        String json = "";
        try {
            json = objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException jp) {
            throw new Exception(jp.getMessage());
        }
        try {
            rabbit.convertAndSend("category-events", "", json);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @RabbitListener(queues = "inverntory-service-category-service")
    public void checkListner(String message) throws Exception {
        UnitInStock inStock = null;
        try {
            inStock = objectMapper.readValue(message, UnitInStock.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(inStock.getUnitInStock()) == 0) {
            productOutOfStock(inStock);
        } else if (Integer.parseInt(inStock.getUnitInStock()) > 0) {
            productInStock(inStock);
        }
    }


    public void productOutOfStock(UnitInStock inStock) throws Exception {

        Maybe<Product> product = productService.getById(Integer.parseInt(inStock.getId()));
        if (product.isEmpty().blockingGet()) {
            throw new Exception("Not Found");
        }
        product.blockingGet().setInStock(false);
        productService.save(product.blockingGet());
    }

    public void productInStock(UnitInStock inStock) throws Exception {
        Maybe<Product> product = productService.getById(Integer.parseInt(inStock.getId()));
        if (product.isEmpty().blockingGet()) {
            throw new Exception("Not Found");
        }
        product.blockingGet().setInStock(true);
        productService.save(product.blockingGet());
    }


}
