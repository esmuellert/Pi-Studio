package site.pistudio.backend.service;

import org.springframework.data.repository.CrudRepository;
import site.pistudio.backend.entities.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Override
    public List<Order> findAll();

    public Order findByOrderNumber(long id);
}