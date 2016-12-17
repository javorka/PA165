package cz.muni.pa165.pneuservis.persistence.repository;

import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Michal Travnicek
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDateCreatedBetween(Date start, Date end);

    List<Order> findByUser(User user);
}
