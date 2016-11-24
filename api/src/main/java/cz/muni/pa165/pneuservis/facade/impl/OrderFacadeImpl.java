package cz.muni.pa165.pneuservis.facade.impl;

import cz.muni.pa165.pneuservis.dto.OrderDTO;
import cz.muni.pa165.pneuservis.facade.OrderFacade;
import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.service.OrderService;
import cz.muni.pa165.pneuservis.service.BeanMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Michal Travnicek xtravni2
 */
@Service
@Transactional
public class OrderFacadeImpl implements OrderFacade {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public OrderService service;
    @Inject
    public BeanMappingService mappingService;

    public OrderDTO save(OrderDTO dto) {
        logger.info("Requested to save Order : {}", dto);
        Order o = mappingService.mapTo(dto, Order.class);
        return mappingService.mapTo(service.save(o), OrderDTO.class);
    }

    public OrderDTO findOne(Long id) {
        logger.info("Requested to find Order with id : {}", id);
        return mappingService.mapTo(service.findOne(id), OrderDTO.class);
    }

    public List<OrderDTO> findAll() {
        logger.info("Requested to find all Orders");
        return mappingService.mapTo(service.findAll(), OrderDTO.class);
    }    

    public void delete(Long id) {
        logger.info("Requested to delete Order with id : {}", id);
        service.delete(id);
    }
}
