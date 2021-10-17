package site.pistudio.backend.services;

import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.mysql.OrderRepository;
import site.pistudio.backend.dao.mysql.ScheduleRepository;
import site.pistudio.backend.dto.mysql.OrderClientBody;
import site.pistudio.backend.dto.mysql.OrderForm;
import site.pistudio.backend.entities.mysql.Order;
import site.pistudio.backend.entities.mysql.Schedule;
import site.pistudio.backend.exceptions.InvalidTokenException;
import site.pistudio.backend.utils.OrderStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class OrderService {
    final
    OrderRepository orderRepository;

    VerifyTokenService verifyTokenService;

    ScheduleRepository scheduleRepository;

    public OrderService(OrderRepository orderRepository,
                        VerifyTokenService verifyTokenService,
                        ScheduleRepository scheduleRepository) {
        this.orderRepository = orderRepository;
        this.verifyTokenService = verifyTokenService;
        this.scheduleRepository = scheduleRepository;
    }


    public OrderClientBody placeOrder(OrderForm orderForm) {
        Order order = new Order();
        String openId = verifyTokenService.verifyToken(orderForm.getToken());
        long orderNumber = generateValidOrderNumber();

        order.setOrderNumber(orderNumber);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setWechatId(orderForm.getWechatId());
        order.setOpenId(openId);
        order.setPhoneNumber(orderForm.getPhoneNumber());
        order.setType(orderForm.getType());
        order.setNotes(orderForm.getNotes());
        order.setOrderedTime(LocalDateTime.now());
        orderRepository.save(order);

        for (LocalDateTime time : orderForm.getSchedule()) {
            Schedule schedule = new Schedule();
            schedule.setTime(time);
            schedule.setOrder(order);
            scheduleRepository.save(schedule);
        }
        OrderClientBody orderClientBody = OrderClientBody.orderToClientBody(order);
        orderClientBody.setSchedule(orderForm.getSchedule());
        return orderClientBody;
    }

    public OrderClientBody getOrderByOrderNumber(long orderNumber, String openId) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        checkIfValidOrder(orderNumber, openId, order);
        List<Schedule> schedules = scheduleRepository
                .findSchedulesByOrder_OrderNumberOrderByTime(order.getOrderNumber());
        return OrderClientBody.OrderToClientBody(order, schedules);
    }

    static void checkIfValidOrder(long orderNumber, String openId, Order order) {

        if (order == null) {
            throw new NoSuchElementException("Order: " + orderNumber + " not found!");
        }
        if (!openId.equals(order.getOpenId()) && !openId.equals("admin")) {
            throw new InvalidTokenException(openId);
        }
    }

    public List<OrderClientBody> getOrdersByOpenId(String openId) {
        List<Order> orders = orderRepository.findOrdersByOpenIdOrderByOrderedTime(openId);
        return ordersToClientBodies(orders);
    }

    public List<OrderClientBody> getOrdersForAdmin(OrderStatus status) {
        List<Order> orders = orderRepository.findAllByOrderByOrderedTime();
        return ordersToClientBodies(orders);
    }

    private List<OrderClientBody> ordersToClientBodies(List<Order> orders) {
        List<OrderClientBody> orderClientBodies = new ArrayList<>();
        for (Order order : orders) {
            List<Schedule> schedules =
                    scheduleRepository.findSchedulesByOrder_OrderNumberOrderByTime(order.getOrderNumber());
            orderClientBodies.add(OrderClientBody.OrderToClientBody(order, schedules));
        }
        return orderClientBodies;
    }

    public OrderClientBody setOrderStatus(LocalDateTime schedule, Long orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);

        switch (order.getOrderStatus()) {
            case PLACED:
                if (schedule == null ||
                        scheduleRepository.findScheduleByOrder_OrderNumberAndTime(orderNumber, schedule) == null) {
                    throw new IllegalArgumentException("Provided schedule is invalid!");
                }
                List<LocalDateTime> schedules = new LinkedList<>();
                schedules.add(schedule);
                order.setOrderStatus(OrderStatus.RECEIVED);
                scheduleRepository.deleteScheduleByOrder_OrderNumberAndTimeNotIn(orderNumber, schedules);
                break;
            case RECEIVED:
                order.setOrderStatus(OrderStatus.IMAGING);
                break;
            case IMAGING:
                order.setOrderStatus(OrderStatus.PROCESSING);
                break;
            case PROCESSING:
                order.setOrderStatus(OrderStatus.FINISHED);
                break;
            default:
                throw new IllegalArgumentException("Order status is not valid!");
        }

        orderRepository.save(order);
        return OrderClientBody.OrderToClientBody(order,
                scheduleRepository.findSchedulesByOrder_OrderNumberOrderByTime(orderNumber));
    }


    private long generateValidOrderNumber() {
        int bound = 10000;
        int failure = 0;
        long id;
        do {
            if (failure > 3) {
                bound *= 10;
                failure = 0;
            }
            id = generateOrderNumber(bound);
            if (orderRepository.findByOrderNumber(id) != null) {
                failure++;
            } else {
                failure = 0;
            }
        } while (failure > 0);
        return id;
    }

    private long generateOrderNumber(int bound) {
        LocalDateTime now = LocalDateTime.now();
        String year = String.format("%02d", now.getYear() % 2000);
        String day = String.format("%03d", now.getDayOfYear());
        String hour = String.format("%02d", now.getHour());
        String time = year + day + hour;
        int suffix = new Random(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).nextInt(bound);
        return Long.parseLong(time + String.format("%0" + (Integer.toString(bound).length() - 1) + "d", suffix));
    }

}
