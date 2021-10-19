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

    public OrderResponse(long orderNumber, OrderStatus orderStatus, String type, LocalDateTime orderedTime,
                         String phoneNumber, String notes, String wechatId,
                         List<LocalDateTime> schedule) {
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.type = type;
        this.orderedTime = orderedTime;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.wechatId = wechatId;
        this.schedule = schedule;
    }

    public static OrderResponse orderToResponse(Order order) {
        return setBodyFromOrder(order);
    }

    private static OrderResponse setBodyFromOrder(Order order) {
        List<LocalDateTime> times = new ArrayList<>();
        for (Schedule schedule : order.getSchedules()) {
            times.add(schedule.getTime());
        }
        OrderResponse orderResponse = new OrderResponse(order.getOrderNumber(), order.getOrderStatus(),
                order.getType(), order.getOrderedTime(), order.getPhoneNumber(), order.getNotes(), order.getWechatId(),
                times);
        ;
        return orderResponse;
    }

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
}
