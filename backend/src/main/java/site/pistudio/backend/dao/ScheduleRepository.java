package site.pistudio.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.Schedule;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    //    @Query(value = "SELECT  * FROM schedule JOIN customer_order ON schedule.order_number = customer_order" +
//            ".order_number WHERE customer_order.order_number = ?",
//            nativeQuery =
//                    true)
    List<Schedule> findSchedulesByOrder_OrderNumberOrderByTime(long id);

    void deleteScheduleByOrder_OrderNumberAndTimeNotIn(long id, List<LocalDateTime> schedule);

    Schedule findScheduleByOrder_OrderNumberAndTime(long id, LocalDateTime time);
}
