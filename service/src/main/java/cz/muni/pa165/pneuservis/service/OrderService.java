package cz.muni.pa165.pneuservis.service;

import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.User;

import java.util.List;

/**
 * @author Michal Travnicek xtravni2 23/11/2016
 */
public interface OrderService {
    /**
     * Saves a given Order. Use the returned instance for further operations
     * as the save operation might have changed the entity instance completely.
     *
     * @param order Order to save
     * @return the saved Order
     */
    Order save(Order order);

    /**
     * Retrieves an Order by its id.
     *
     * @param id must not be null.
     * @return the Order with the given id or null if none found
     */
    Order findOne(Long id);

    /**
     * Returns all instances of the Order.
     *
     * @return all entities
     */
    List<Order> findAll();

    /**
     * Deletes the Order with the given id.
     *
     * @param id - must not be null.
     */
    void delete(Long id);

    List<Order> findByUser(User user);
}
