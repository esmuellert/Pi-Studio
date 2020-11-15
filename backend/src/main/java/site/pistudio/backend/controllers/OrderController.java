package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.dto.OrderClientBody;
import site.pistudio.backend.dto.OrderForm;
import site.pistudio.backend.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderClientBody> placeOrder(@RequestBody OrderForm orderForm,
                                                      @RequestHeader(name = "Authorization") String token) {
        orderForm.setToken(token);
        OrderClientBody order = orderService.placeOrder(orderForm);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderClientBody> getOrderById(@PathVariable("id") Long id,
                                                        @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok().body(orderService.getOrderByOrderNumber(id, token));
    }

    @GetMapping
    public ResponseEntity<List<OrderClientBody>> getOrders(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok().body(orderService.getOrders(token));
    }


}
