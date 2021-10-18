package site.pistudio.backend.dao.firestore;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.pistudio.backend.entities.firestore.Message;

import java.util.List;

public interface MessageRepository extends DatastoreRepository<Message, Long> {
    Page<Message> findByOrderNumberOrderByTimeAsc(long id, Pageable pageable);
    List<Message> findByOrderNumberOrderByTimeAsc(long id);
}
