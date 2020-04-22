package com.northwind.orderservice.workers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.northwind.orderservice.domain.Order;
import com.northwind.orderservice.domain.OrderItem;
import com.northwind.orderservice.services.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderEventsWorker {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Log logger;
    private OrderService service;
    private RabbitTemplate rabbitTemplate;

    public OrderEventsWorker(OrderService service, RabbitTemplate rabbitTemplate) {
        this.logger = LogFactory.getLog(OrderEventsWorker.class);
        this.service = service;
        this.rabbitTemplate = rabbitTemplate;
    }


    @RabbitListener(queues = "shipping-events-orders-service")
    public void receiveEvent(String message) {
        try {
            ShippingEvent event = objectMapper.readValue(message, ShippingEvent.class);

            Order order = service.get(Long.parseLong(event.getData().get("orderNo").toString())).get();
            order.orderShipped(new Date(Long.parseLong(event.getData().get("shippedDate").toString())), 0);

            service.save(order);
        } catch (JsonProcessingException e) {
            logger.warn(String.format("Unable to process event from shipping: %s", message), e);
        }
    }


    public void notifyOrderPlaced(Order order) throws JsonProcessingException {
        ShippingEvent event = new ShippingEvent();
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", String.valueOf(order.getId()));
        map.put("shipAddress", order.getShipAddress());
        map.put("shipCity", order.getShipCity());
        map.put("shipCountry", order.getShipCountry());
        map.put("shipRegion", order.getShipRegion());
        map.put("shipPostalCode", order.getShipPostalCode());
        map.put("shipName", order.getShipName());
        event.setData(map);
        order.getItems().stream().forEach(p -> {
            Map<String, Object> map2 = new HashMap<>();
            map2.put("productName", p.getProductName());
            map2.put("quantity", String.valueOf(p.getQuantity()));
            OrderDetailsEvent orderDetailsEvent = new OrderDetailsEvent();
            orderDetailsEvent.setOrderDetails(map2);
            event.getOrderDetails().add(orderDetailsEvent);
        });
        String json = objectMapper.writeValueAsString(event);
        rabbitTemplate.convertAndSend("order-events", "", json);
    }



    public void updateInventory(Order order){
        OrderDetailsEvent orderDetailsEvent = new OrderDetailsEvent();
        for (OrderItem o : order.getItems()) {
            orderDetailsEvent.getOrderDetails().put(String.valueOf(o.getId().getProductId()), String.valueOf(o.getQuantity()));
        }
        String json = null;
        try {
            json = objectMapper.writeValueAsString(orderDetailsEvent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("order-events", "", json);
    }

}
