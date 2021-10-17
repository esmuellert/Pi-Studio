package site.pistudio.backend.dao.firestore;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.pistudio.backend.entities.firestore.Message;

import java.util.List;

public interface MessageRepository extends DatastoreRepository<Message, Long> {
    Page<Message> findByOrder_OrderNumberOrderByTimeAsc(long id, Pageable pageable);
    List<Message> findByOrder_OrderNumberOrderByTimeAsc(long id);
}
