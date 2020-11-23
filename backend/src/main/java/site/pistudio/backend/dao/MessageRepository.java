package site.pistudio.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import site.pistudio.backend.entities.Message;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Page<Message> findByOrder_OrderNumberOrderByTimeDesc(long id, Pageable pageable);
}
