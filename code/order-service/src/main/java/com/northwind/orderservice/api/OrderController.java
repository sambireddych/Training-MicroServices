package com.northwind.orderservice.api;

import com.northwind.orderservice.domain.Order;
import com.northwind.orderservice.services.MonthComparisionModel;
import com.northwind.orderservice.services.OrderByMonthModel;
import com.northwind.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/orders",produces = "application/json")
public class OrderController {
    private OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }


    @GetMapping(path = "/orderhistory/{customerId}", params = "month",produces = "application/json")
    public ResponseEntity<List<OrderByMonthModel>> get(@PathVariable long customerId, @RequestParam String month) {
        String customerNo = service.findByCustomerId(customerId).get(0).getCustomerNo();
        if (customerNo.isEmpty() || customerNo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OrderByMonthModel> allOrders = service.getAllOrders(customerNo, month);
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping(path = "/orderhistory/{customerId}",produces = "application/json")
    public ResponseEntity<List<MonthComparisionModel>> getByMonth(@PathVariable long customerId) {
        String customerNo = service.findByCustomerId(customerId).get(0).getCustomerNo();
        if (customerNo.isEmpty() || customerNo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(service.getDataByMonths(customerNo), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderModel>> get(
            @RequestParam(required = false) Optional<Integer> offset,
            @RequestParam(required = false) Optional<Integer> limit) {

        List<OrderModel> orders = service.getAll(offset.orElse(0), limit.orElse(10))
                .stream().map(o -> OrderMapper.toModel(o))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<OrderModel> get(@PathVariable long orderNo) {
        Optional<Order> order = service.get(orderNo);
        if (!order.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(OrderMapper.toModel(order.get()));
    }

    @PostMapping
    public ResponseEntity<OrderModel> create(@RequestBody OrderModel model) {
        Order order = OrderMapper.toNewEntity(model);

        OrderModel savedOrder =
                OrderMapper.toModel(service.save(order));

        return ResponseEntity.created(URI.create("/orders/" + order.getId()))
                .body(savedOrder);
    }

    @PutMapping("/{orderNo}")
    public ResponseEntity<OrderModel> update(@PathVariable long orderNo, @RequestBody OrderModel model) {
        Optional<Order> order = service.get(orderNo);
        if (!(order.isPresent()))
            return ResponseEntity.notFound().build();

        if (order.get().getVersion() != model.getVersion())
            return new ResponseEntity<>(OrderMapper.toModel(order.get()), HttpStatus.CONFLICT);

        OrderMapper.merge(model, order.get());
        return ResponseEntity.ok(OrderMapper.toModel(service.save(order.get())));
    }

    @DeleteMapping("/{orderNo}")
    public ResponseEntity delete(@PathVariable long orderNo) {
        service.delete(orderNo);
        return ResponseEntity.noContent().build();
    }
}
