package site.pistudio.backend.dto.firestore;

import site.pistudio.backend.entities.firestore.Order;
import site.pistudio.backend.entities.firestore.Schedule;
import site.pistudio.backend.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderResponse {
    private long orderNumber;
    private OrderStatus orderStatus;
    private String type;
    private LocalDateTime orderedTime;
    private String phoneNumber;
    private String notes;
    private String wechatId;
    private List<LocalDateTime> schedule;

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<LocalDateTime> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LocalDateTime> schedule) {
        this.schedule = schedule;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public static OrderResponse OrderToResponse(Order order, List<Schedule> schedules) {
        OrderResponse orderResponse =  setBodyFromOrder(order);
        List<LocalDateTime> times = new ArrayList<>();
        for (Schedule schedule : schedules) {
            times.add(schedule.getTime());
        }
        orderResponse.setSchedule(times);
        return orderResponse;
    }

    public static OrderResponse orderToResponse(Order order) {
        return setBodyFromOrder(order);
    }

    private static OrderResponse setBodyFromOrder(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setNotes(order.getNotes());
        orderResponse.setOrderedTime(order.getOrderedTime());
        orderResponse.setOrderNumber(order.getOrderNumber());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setPhoneNumber(order.getPhoneNumber());
        orderResponse.setType(order.getType());
        orderResponse.setWechatId(order.getWechatId());
        return orderResponse;
    }
}
