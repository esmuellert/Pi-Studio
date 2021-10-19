package site.pistudio.backend.dao.firestore;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.firestore.Order;

import java.util.List;

@Repository
public interface OrderRepository extends DatastoreRepository<Order, Long> {
    @Override
    List<Order> findAll();

    Order findByOrderNumber(long id);

    List<Order> findOrdersByOpenIdOrderByOrderedTime(String id);

    @Query("SELECT * FROM `order` ORDER BY orderedTime")
    List<Order> findAllOrderByOrderedTime();

    List<Order> findOrderByOpenId(String id);

}