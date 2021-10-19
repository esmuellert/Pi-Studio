package site.pistudio.backend.dao.firestore;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.firestore.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ScheduleRepository extends DatastoreRepository<Schedule, Long> {

    List<Schedule> findSchedulesByOrderNumberOrderByTime(long orderNumber);

    void deleteScheduleByOrderNumberAndTimeNotIn(Long orderNumber, List<LocalDateTime> schedules);

    Schedule findScheduleByOrderNumberAndTime(Long orderNumber, LocalDateTime schedule);

    int deleteByOrderNumberAndTimeGreaterThanAndTimeLessThan(long orderNumber, LocalDateTime time, LocalDateTime time2);

}
