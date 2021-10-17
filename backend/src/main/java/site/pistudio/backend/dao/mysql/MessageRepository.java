package site.pistudio.backend.dao.mysql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import site.pistudio.backend.entities.mysql.Message;

import java.util.List;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Page<Message> findByOrder_OrderNumberOrderByTimeAsc(long id, Pageable pageable);
    List<Message> findByOrder_OrderNumberOrderByTimeAsc(long id);
}
