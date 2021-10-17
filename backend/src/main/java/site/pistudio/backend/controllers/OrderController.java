package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.dto.firestore.OrderResponse;
import site.pistudio.backend.dto.firestore.OrderRequest;
import site.pistudio.backend.exceptions.InvalidTokenException;
import site.pistudio.backend.services.OrderService;
import site.pistudio.backend.services.VerifyTokenService;

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
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest,
                                    @RequestHeader(name = "Authorization") String token) {
        orderRequest.setToken(token);
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable("id") Long id,
                                      @RequestHeader(name = "Authorization") String token) {
        String openId = verifyTokenService.verifyToken(token);
        return orderService.getOrderByOrderNumber(id, openId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders(@RequestHeader(name = "Authorization") String token) {
        String openId = verifyTokenService.verifyToken(token);
        if (openId.equals("admin")) {
            return orderService.getOrdersForAdmin(null);
        }
        return orderService.getOrdersByOpenId(openId);
    }

    @Transactional
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse setOrderStatus(@RequestHeader(name = "Authorization") String token,
                                        @RequestBody(required = false) Map<String, String> body,
                                        @PathVariable(name = "id") Long id) {
        String openId = verifyTokenService.verifyToken(token);
        if (!openId.equals("admin")) {
            throw new InvalidTokenException();
        }

        if (body == null || body.isEmpty()) {
            return orderService.setOrderStatus(null, id);
        }
        LocalDateTime schedule = LocalDateTime.parse(body.get("schedule"));
        return orderService.setOrderStatus(schedule, id);
    }


}
