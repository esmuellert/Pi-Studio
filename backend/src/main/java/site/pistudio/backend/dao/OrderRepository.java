package site.pistudio.backend.dao;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.pistudio.backend.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Override
    List<Order> findAll();

    Order findByOrderNumber(long id);

    List<Order> findOrdersByOpenIdOrderByOrderedTime(String id);

    List<Order> findAllByOrderByOrderedTime();


}