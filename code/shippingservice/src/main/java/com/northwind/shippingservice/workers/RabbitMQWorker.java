package com.northwind.shippingservice.workers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.northwind.shippingservice.domain.PackingSlipDetails;
import com.northwind.shippingservice.domain.PackingSlips;
import com.northwind.shippingservice.service.PackingSlipservice;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RabbitMQWorker {

    private RabbitTemplate rabbit;
    private ObjectMapper objectMapper = new ObjectMapper();
    private PackingSlipservice packingSlipservice;

    @Autowired
    public RabbitMQWorker(RabbitTemplate rabbit, PackingSlipservice packingSlipservice) {
        this.rabbit = rabbit;
        this.packingSlipservice = packingSlipservice;
    }

    public void notifyOrderShipped(long orderId) throws JsonProcessingException {
        ShippingEvent event = new ShippingEvent();
        event.setEventType("OrderShipped");
        event.getData().put("orderNo", orderId);
        event.getData().put("shippedDate", Calendar.getInstance().getTime());

        String json = objectMapper.writeValueAsString(event);
        rabbit.convertAndSend("shipping-events", "", json);
    }


    @RabbitListener(queues = "order-service-shipping-service")
    public void receiveEvent(String message){
        try {
            OrderModel event = objectMapper.readValue(message, OrderModel.class);
            //setting PackingSlips object
//            Map<String,Object> map = event.getData();
            PackingSlips packingSlips = new PackingSlips();
            packingSlips.setOrderID((int) event.getOrderId());
            packingSlips.setShipAddress(event.getShipAddress());
            packingSlips.setShipCity(event.getShipCity());
            packingSlips.setShipCountry(event.getShipCountry());
            packingSlips.setShipRegion(event.getShipRegion());
            packingSlips.setShipPostalCode(event.getShipPostalCode());
            packingSlips.setShipName(event.getShipName());
            //setting PackingSlipDetails
            Set<OrderItemModel> orderDetails = event.getOrderItemModelSet();
            Set<PackingSlipDetails> packingSlipDetailsList = new HashSet<>();
            packingSlips.setAddItems(packingSlipDetailsList);
            orderDetails.stream().forEach(p -> {
                PackingSlipDetails packingSlipDetails = new PackingSlipDetails();
                packingSlipDetails.setProductName(p.getProductName());
                packingSlipDetails.setQuantity(p.getQuantity());
                packingSlips.addItem(packingSlipDetails);
            });
            packingSlipservice.save(packingSlips);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //logger.warn(String.format("Unable to process event from shipping: %s", message), e);
        }
    }
}
