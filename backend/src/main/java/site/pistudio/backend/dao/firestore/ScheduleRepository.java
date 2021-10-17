package site.pistudio.backend.dao.firestore;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.firestore.Schedule;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ScheduleRepository extends DatastoreRepository<Schedule, Long> {

    List<Schedule> findSchedulesByOrderNumberOrderByTime(long orderNumber);

    void deleteScheduleByOrderNumberAndTimeNotIn(Long orderNumber, List<LocalDateTime> schedules);

    Object findScheduleByOrderNumberAndTime(Long orderNumber, LocalDateTime schedule);
}
