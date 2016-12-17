package cz.muni.pa165.pneuservis.service.impl;

import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.repository.OrderRepository;
import cz.muni.pa165.pneuservis.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Michal Travnicek xtravni2
 */
@Service
public class OrderServiceImpl implements OrderService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private OrderRepository orderRepository;

	@Override
	public Order save(Order order) {
		logger.info("Requested to save Order: {}", order);
		return orderRepository.save(order);
	}

	@Override
	public Order findOne(Long id) {
		logger.info("Requested to find Order with id : {}", id);
		return orderRepository.findOne(id);
	}

	@Override
	public List<Order> findAll() {
		logger.info("Requested to find all Orders");
		return orderRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		logger.info("Requested to delete Order with id: {} ", id);
		orderRepository.delete(id);
	}

	@Override
	public List<Order> findByUser(User user) {
		return orderRepository.findByUser(user);
	}
}
