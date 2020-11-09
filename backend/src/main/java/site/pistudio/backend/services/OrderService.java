package site.pistudio.backend.services;

import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.OrderRepository;
import site.pistudio.backend.entities.Order;

import java.util.Calendar;
import java.util.Random;

@Service
public class OrderService {
    final
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    private long generateOrderNumber(int bound) {
        Calendar calendar = Calendar.getInstance();
        String year = String.format("%02d", calendar.get(Calendar.YEAR) % 2000);
        String day = String.format("%03d", calendar.get(Calendar.DAY_OF_YEAR) + 1);
        String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
        String time = year + day + hour;
        int suffix = new Random(calendar.getTimeInMillis()).nextInt(bound);
        return Long.parseLong(time + String.format("%0" + (Integer.toString(bound).length() - 1) + "d", suffix));
    }

    public Order placeOrder(Order order) {
        int bound = 10000;
        int failure = 0;
        do {
            if (failure > 3) {
                bound *= 10;
                failure = 0;
            }
            long id = generateOrderNumber(bound);
            if (orderRepository.findByOrderNumber(id) != null) {
                failure++;
            } else {
                order.setOrderNumber(id);
                failure = 0;
            }
        } while (failure > 0);
        return orderRepository.save(order);
    }
}
