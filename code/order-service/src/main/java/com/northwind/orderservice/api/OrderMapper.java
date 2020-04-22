package com.northwind.orderservice.api;

import com.northwind.orderservice.domain.Order;
import com.northwind.orderservice.services.MonthComparisionModel;
import com.northwind.orderservice.services.OrderByMonthModel;
import com.northwind.orderservice.services.OrderService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderMapper {
    public static OrderModel toModel(Order entity) {
        OrderModel model = new OrderModel();
        model.setOrderId(entity.getId());
        model.setCustomerNo(entity.getCustomerNo());
        model.setCompanyName(entity.getCompanyName());
        model.setOrderDate(entity.getOrderDate());
        model.setTotal(entity.getOrderTotal());
        model.setVersion(entity.getVersion());
        model.setShipAddress(entity.getShipAddress());
        model.setShipCity(entity.getShipCity());
        model.setShipCountry(entity.getShipCountry());
        model.setShipPostalCode(entity.getShipPostalCode());
        model.setShipRegion(entity.getShipRegion());
        model.setShipName(entity.getShipName());
        model.setStatus(entity.getStatus());
        model.setShippedDate(entity.getShippedDate());
        model.setOrderItemModelSet(entity.getItems().stream().map(orderItem -> OrderItemMapper.toModel(orderItem)).collect(Collectors.toSet()));
        return model;
    }


    public static OrderByMonthModel toModelConvert(Order entity) {
        OrderByMonthModel model = new OrderByMonthModel();
        model.setOrderNo(entity.getId());
        model.setCustomerNo(entity.getCustomerNo());
        model.setCompanyName(entity.getCompanyName());
        model.setOrderByMonth(OrderService.getMonth(entity.getOrderDate()));
        model.setTotal(entity.getOrderTotal());
        model.setVersion(entity.getVersion());
        model.setShipAddress(entity.getShipAddress());
        model.setShipCity(entity.getShipCity());
        model.setShipCountry(entity.getShipCountry());
        model.setShipPostalCode(entity.getShipPostalCode());
        model.setShipRegion(entity.getShipRegion());
        model.setShipName(entity.getShipName());
        model.setStatus(entity.getStatus());
        model.setShippedDate(entity.getShippedDate());
        model.setOrderItemList(entity.getItems().stream().map(orderItem -> OrderItemMapper.toModel(orderItem)).collect(Collectors.toList()));
        return model;
    }


    public static List<MonthComparisionModel> mapToModel(Stream<Map.Entry<String, BigDecimal>> map){
        List<MonthComparisionModel> list = new LinkedList<>();
//        Iterator<Map.Entry<String, BigDecimal>> iterator = map.iterator();
       map.forEach(stringBigDecimalEntry -> {
           MonthComparisionModel model = new MonthComparisionModel();
           model.setMonth(stringBigDecimalEntry.getKey());
           model.setTotalAmount(stringBigDecimalEntry.getValue());
           list.add(model);
       });
        /*while (iterator.hasNext()){
            Map.Entry<String, BigDecimal> next = iterator.next();
            model.setMonth(next.getKey());
            model.setTotalAmount(next.getValue());
            list.add(model);
        }*/
        return list;
    }



    public static MonthComparisionModel toModelCompareAndMerge(OrderByMonthModel model){
        MonthComparisionModel comparisionModel = new MonthComparisionModel();
        /*Map<String, BigDecimal> map = new HashMap<>();
        if(map.containsKey(model.getOrderByMonth())){
            map.compute(model.getOrderByMonth(),(s, bigDecimal) -> bigDecimal.add(model.getTotal()));
        }
        map.put(model.getOrderByMonth(),model.getTotal());
        comparisionModel.setMonth(model.getOrderByMonth());
        comparisionModel.setTotalAmount(map.get(model.getOrderByMonth()));*/
        comparisionModel.setMonth(model.getOrderByMonth());
        comparisionModel.setTotalAmount(model.getTotal());
        return comparisionModel;
    }

    public static Order toNewEntity(OrderModel model) {
        Order entity = new Order(model.getCustomerId(),
                                 model.getCustomerNo(),
                                 model.getCompanyName());

        entity.setOrderDate(new Date(model.getOrderDate().getTime()));
        entity.setShipAddress(model.getShipAddress());
        entity.setShipCity(model.getShipCity());
        entity.setShipCountry(model.getShipCountry());
        entity.setShipPostalCode(model.getShipPostalCode());
        entity.setShipRegion(model.getShipRegion());
        entity.setShipName(model.getShipName());
        return entity;
    }

    public static void merge(OrderModel model, Order entity) {
        entity.setShipAddress(model.getShipAddress());
        entity.setShipCity(model.getShipCity());
        entity.setShipCountry(model.getShipCountry());
        entity.setShipPostalCode(model.getShipPostalCode());
        entity.setShipRegion(model.getShipRegion());
        entity.setShipName(model.getShipName());
    }
}
