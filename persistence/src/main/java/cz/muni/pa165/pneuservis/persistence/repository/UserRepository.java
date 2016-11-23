package cz.muni.pa165.pneuservis.persistence.repository;

import cz.muni.pa165.pneuservis.persistence.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
