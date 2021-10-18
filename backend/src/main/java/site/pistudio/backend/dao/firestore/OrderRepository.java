package site.pistudio.backend.dao.firestore;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.firestore.Order;

import java.util.List;

@Repository
public interface OrderRepository extends DatastoreRepository<Order, Long> {
    @Override
    List<Order> findAll();

    Order findByOrderNumber(long id);

    List<Order> findOrdersByOpenIdOrderByOrderedTime(String id);

    List<Order> findAllByOrderByOrderedTime();


}