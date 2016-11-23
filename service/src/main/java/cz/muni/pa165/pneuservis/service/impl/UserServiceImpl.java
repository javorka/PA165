package cz.muni.pa165.pneuservis.service.impl;

import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.repository.UserRepository;
import cz.muni.pa165.pneuservis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
@Named
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UserRepository repository;

	@Override
	public User save(User user) {
		logger.info("Requested to save User : {}", user);
		return repository.save(user);
	}

	@Override
	public User findOne(Long id) {
		logger.info("Requested to find User with id : {}", id);
		return repository.findOne(id);
	}

	@Override
	public User findByEmail(String email) {
		logger.info("Requested to find AdditionalServices by email : {}", email);
		return repository.findByEmail(email);
	}

	@Override
	public List<User> findAll() {
		logger.info("Requested to find all Users");
		return repository.findAll();
	}

	@Override
	public void delete(Long id) {
		logger.info("Requested to delete User with id: {} ", id);
		repository.delete(id);
	}
}
