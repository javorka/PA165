package cz.muni.pa165.pneuservis.backend.repository;

import cz.muni.pa165.pneuservis.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Michal Travnicek
 */
public interface OrderRepository extends JpaRepository<User, Long> {
}
