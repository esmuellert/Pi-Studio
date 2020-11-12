package site.pistudio.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @Column(unique = true)
    private long orderNumber;

//    @Column(unique = true)
    private String openId;
    


    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String userId) {
        this.openId = userId;
    }

}
