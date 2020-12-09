package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.dto.OrderClientBody;
import site.pistudio.backend.dto.OrderForm;
import site.pistudio.backend.exceptions.InvalidTokenException;
import site.pistudio.backend.services.OrderService;
import site.pistudio.backend.services.VerifyTokenService;
import site.pistudio.backend.utils.OrderStatus;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final VerifyTokenService verifyTokenService;

    public OrderController(OrderService orderService,
                           VerifyTokenService verifyTokenService) {
        this.orderService = orderService;
        this.verifyTokenService = verifyTokenService;
    }


    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderClientBody placeOrder(@RequestBody OrderForm orderForm,
                                      @RequestHeader(name = "Authorization") String token) {
        orderForm.setToken(token);
        return orderService.placeOrder(orderForm);
    }

    @GetMapping("/{id}")
    public OrderClientBody getOrderById(@PathVariable("id") Long id,
                                        @RequestHeader(name = "Authorization") String token) {
        String openId = verifyTokenService.verifyToken(token);
        return orderService.getOrderByOrderNumber(id, openId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderClientBody> getOrders(@RequestHeader(name = "Authorization") String token) {
        String openId = verifyTokenService.verifyToken(token);
        if (openId.equals("admin")) {
            return orderService.getOrdersForAdmin(null);
        }
        return orderService.getOrdersByOpenId(openId);
    }
    @Transactional
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderClientBody setOrderStatus(@RequestHeader(name = "Authorization") String token,
                                          @RequestBody Map<String, String> body,
                                              @PathVariable(name = "id") Long id) {
        String openId = verifyTokenService.verifyToken(token);
        if (!openId.equals("admin")) {
            throw new InvalidTokenException();
        }
        LocalDateTime schedule = LocalDateTime.parse(body.get("schedule"));
        return orderService.setOrderStatus(schedule, id);
    }


}
