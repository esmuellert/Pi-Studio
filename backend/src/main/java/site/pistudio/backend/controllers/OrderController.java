package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.dao.OrderRepository;
import site.pistudio.backend.dto.OrderForm;
import site.pistudio.backend.entities.Order;
import site.pistudio.backend.services.OrderService;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> create(@RequestBody OrderForm orderForm) {
        Order order = orderService.placeOrder(orderForm);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id, HttpServletResponse response) {
        Order order = orderRepository.findByOrderNumber(id);
        if (order == null) {
            throw new NoSuchElementException();
        }
        return ResponseEntity.ok().body(order);
    }
}
