package site.pistudio.backend.dao;

import org.springframework.data.repository.CrudRepository;
import site.pistudio.backend.entities.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Override
    List<Order> findAll();

    Order findByOrderNumber(long id);
}