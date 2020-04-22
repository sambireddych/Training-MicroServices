package com.northwind.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.northwind.orderservice.adapters.ShippingServiceClient;
import com.northwind.orderservice.api.OrderMapper;
import com.northwind.orderservice.domain.Order;
import com.northwind.orderservice.repositories.OrderRepository;
import com.northwind.orderservice.workers.OrderDetailsEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {
    private OrderRepository repository;
    private ShippingServiceClient shippingClient;

    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderService(OrderRepository repository, ShippingServiceClient shippingClient, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.repository = repository;
        this.shippingClient = shippingClient;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public List<Order> getAll(int offset, int limit) {
        int page = 1;
        if (offset > 0)
            page = (offset / limit) + 1;

        return repository.findAll(PageRequest.of(page, limit)).toList();
    }

    public Optional<Order> get(long id) {
        return repository.findById(id);
    }

   /* public Order save(Order entity) {
        BigDecimal freight = shippingClient.getFreightAmount(entity.getShipCountry());
        entity.setFreight(freight);
        return repository.saveAndFlush(entity);
    }*/

    public Order save(Order entity) {
        boolean isNew = false;
        if (entity.getId() == 0){
            isNew = true;
            BigDecimal freight = shippingClient.getFreightAmount(entity.getShipCountry());
            entity.setFreight(freight);
        }
        entity = repository.saveAndFlush(entity);
        try {
            OrderDetailsEvent event = new OrderDetailsEvent();
            event.getOrderDetails().put("order", OrderMapper.toModel(entity));
            if (isNew) {
                // OrderCreated event
                event.setEventType("OrderPlaced");
            } else {
                //OrderUpdated event
                event.setEventType("OrderUpdated");
            }
            rabbitTemplate.convertAndSend("order-events", "", objectMapper.writeValueAsString(OrderMapper.toModel(entity)));
            //  rabbitTemplate.convertAndSend("order-service","InventoryService",objectMapper.writeValueAsString(OrderMapper.toModel(entity)));
        } catch (JsonProcessingException e) {
//            logger.debug("An error occurred serializing Order for event.", e);
        }
        return entity;
    }

    public void delete(long id) {
        repository.deleteById(id);
        repository.flush();
    }


    public List<Order> findByCustomerId(long customerId) {
        return repository.findByCustomerId(customerId);
    }


    public List<MonthComparisionModel> getDataByMonths(String customerNo) {
        List<MonthComparisionModel> collect = repository.findByCustomerNo(customerNo).stream()
                .map(order -> OrderMapper.toModelConvert(order))
                .map(orderByMonthModel -> OrderMapper.toModelCompareAndMerge(orderByMonthModel))
                .collect(Collectors.toList());
              /*  .forEach(monthComparisionModel -> {
                    if(map.containsKey(monthComparisionModel.getMonth())){
                        map.compute(monthComparisionModel.getMonth(),(s, bigDecimal) -> bigDecimal.add(monthComparisionModel.getTotalAmount()));
                    }else{
                        map.put(monthComparisionModel.getMonth(),monthComparisionModel.getTotalAmount());
                    }
                });

*/

        Map<String, BigDecimal> map = new HashMap<>();
        for (MonthComparisionModel comparisionModel : collect) {
            if (map.containsKey(comparisionModel.getMonth())) {
                map.compute(comparisionModel.getMonth(), (s, bigDecimal1) -> bigDecimal1.add(comparisionModel.getTotalAmount()));
            } else {
                map.put(comparisionModel.getMonth(), comparisionModel.getTotalAmount());
            }
        }
        Stream<Map.Entry<String, BigDecimal>> sorted =
                map.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()));

        return OrderMapper.mapToModel(sorted);


    }


    public List<OrderByMonthModel> getAllOrders(String customerNo, String month) {
        return repository.findByCustomerNo(customerNo).stream()
                .map(order -> OrderMapper.toModelConvert(order))
                .filter(orderByMonthModel -> orderByMonthModel.getOrderByMonth().equals(month))
                .collect(Collectors.toList());
    }

    public static String getMonth(Date date) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("MM");
        return simpleDateformat.format(date);
    }

}
