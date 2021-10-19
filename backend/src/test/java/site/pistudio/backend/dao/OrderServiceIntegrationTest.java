package site.pistudio.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.pistudio.backend.dao.firestore.OrderRepository;
import site.pistudio.backend.dao.firestore.ScheduleRepository;
import site.pistudio.backend.entities.firestore.Order;
import site.pistudio.backend.entities.firestore.Schedule;
import site.pistudio.backend.services.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceIntegrationTest {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    public void generateManyOrderNumber() {
//            orderService.generateValidOrderNumber();
    }

    @Test
    public void findSchedulesByOrder() {
        List<Schedule> schedules = scheduleRepository.findSchedulesByOrderNumberOrderByTime(20319204432L);
        for (Schedule schedule : schedules) {
            System.out.println(schedule);
        }
    }

    @Test
    public void findOrderByOpenId() {
        List<Order> orders = orderRepository.findOrderByOpenId("oMj9c5HpP1mV6zjQ53UobYd22gFY");
        System.out.println(orders);
    }

    @Test
    public void deleteSchedule() {
//        int schedule = scheduleRepository.deleteByOrderNumberAndTimeGreaterThanAndTimeLessThan(21291178795L,
//                LocalDateTime.parse(
//                        "2020-12-01T12:32:51"), LocalDateTime.parse(
//                        "2020-12-01T12:32:51"));

        scheduleRepository.performTransaction(transactionRepository -> {
            List<Schedule> schedules =
                    orderRepository.findByOrderNumber(21291178795L).getSchedules().stream()
                            .filter(schedule1 -> !schedule1.getTime().equals(LocalDateTime.parse(
                                    "2020-12-01T12:32:51"))).collect(Collectors.toList());
            scheduleRepository.deleteAll(schedules);
            return null;
        });
    }


}
