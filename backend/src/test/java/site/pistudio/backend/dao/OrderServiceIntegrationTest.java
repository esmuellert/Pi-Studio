package site.pistudio.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.pistudio.backend.dao.mysql.OrderRepository;
import site.pistudio.backend.dao.mysql.ScheduleRepository;
import site.pistudio.backend.entities.mysql.Schedule;
import site.pistudio.backend.services.OrderService;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
        List<Schedule> schedules = scheduleRepository.findSchedulesByOrder_OrderNumberOrderByTime(20319204432L);
        for (Schedule schedule:schedules) {
            System.out.println(schedule);
        }
    }


}
