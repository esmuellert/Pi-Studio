package site.pistudio.backend.dao.firestore;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.firestore.Schedule;


@Repository
public interface ScheduleRepository extends DatastoreRepository<Schedule, Long> {


}
