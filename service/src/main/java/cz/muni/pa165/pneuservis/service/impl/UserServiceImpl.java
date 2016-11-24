package cz.muni.pa165.pneuservis.service.impl;

import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.repository.OrderRepository;
import cz.muni.pa165.pneuservis.persistence.repository.UserRepository;
import cz.muni.pa165.pneuservis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
@Named
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UserRepository userRepository;

    @Inject
    private OrderRepository orderRepository;

	@Override
	public User save(User user) {
		logger.info("Requested to save User : {}", user);
		return userRepository.save(user);
	}

	@Override
	public User findOne(Long id) {
		logger.info("Requested to find User with id : {}", id);
		return userRepository.findOne(id);
	}

	@Override
	public User findByEmail(String email) {
		logger.info("Requested to find AdditionalServices by email : {}", email);
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> findAll() {
		logger.info("Requested to find all Users");
		return userRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		logger.info("Requested to delete User with id: {} ", id);
		userRepository.delete(id);
	}

    @Override
    public List<User> findUsersWithOrdersLastSevenDays() {
        Calendar now = Calendar.getInstance();
        Calendar sevenDaysAgo = Calendar.getInstance();
        sevenDaysAgo.add(Calendar.DAY_OF_MONTH, -7);

        List<Order> byDateCreatedBetween = orderRepository.findByDateCreatedBetween(sevenDaysAgo.getTime(), now.getTime());

        return byDateCreatedBetween.stream().map(Order::getUser).collect(Collectors.toList());
    }
}
