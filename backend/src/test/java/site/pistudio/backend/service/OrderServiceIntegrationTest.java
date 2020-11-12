package site.pistudio.backend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.pistudio.backend.entities.Order;
import site.pistudio.backend.services.OrderService;

import static org.junit.Assert.assertEquals;
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceIntegrationTest {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void generateManyOrderNumber() {
        for (int i = 0; i < 1000; i++) {
            Order order = new Order();
            order.setOpenId("Yanuo Ma");
            orderService.placeOrder(order);
        }
        assertEquals(1000, orderRepository.findAll().size());
    }
}
