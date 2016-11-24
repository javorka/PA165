package cz.muni.pa165.pneuservis.service;

import cz.muni.pa165.pneuservis.persistence.domain.Order;
import java.util.List;

/**
 * @author Michal Travnicek xtravni2 23/11/2016
 */
public interface OrderService {
    Order save(Order order);
    Order findOne(Long id);
    List<Order> findAll();
    void delete(Long id);
}
