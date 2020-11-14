package site.pistudio.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.Schedule;
@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
}
