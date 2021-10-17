package site.pistudio.backend.dto.mysql;

import site.pistudio.backend.entities.mysql.Order;
import site.pistudio.backend.entities.mysql.Schedule;
import site.pistudio.backend.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderClientBody {
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

    public static OrderClientBody OrderToClientBody(Order order, List<Schedule> schedules) {
        OrderClientBody orderClientBody =  setBodyFromOrder(order);
        List<LocalDateTime> times = new ArrayList<>();
        for (Schedule schedule : schedules) {
            times.add(schedule.getTime());
        }
        orderClientBody.setSchedule(times);
        return orderClientBody;
    }

    public static OrderClientBody orderToClientBody(Order order) {
        return setBodyFromOrder(order);
    }

    private static OrderClientBody setBodyFromOrder(Order order) {
        OrderClientBody orderClientBody = new OrderClientBody();
        orderClientBody.setNotes(order.getNotes());
        orderClientBody.setOrderedTime(order.getOrderedTime());
        orderClientBody.setOrderNumber(order.getOrderNumber());
        orderClientBody.setOrderStatus(order.getOrderStatus());
        orderClientBody.setPhoneNumber(order.getPhoneNumber());
        orderClientBody.setType(order.getType());
        orderClientBody.setWechatId(order.getWechatId());
        return orderClientBody;
    }
}
