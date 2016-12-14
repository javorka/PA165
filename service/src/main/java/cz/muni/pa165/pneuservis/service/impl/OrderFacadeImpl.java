package cz.muni.pa165.pneuservis.service.impl;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
import cz.muni.pa165.pneuservis.api.facade.OrderFacade;
import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.service.BeanMappingService;
import cz.muni.pa165.pneuservis.service.OrderService;
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
    public OrderService orderService;
    @Inject
    public BeanMappingService mappingService;

    public OrderDTO save(OrderDTO dto) {
        logger.info("Requested to save Order : {}", dto);
        Order o = mappingService.mapTo(dto, Order.class);
        return mappingService.mapTo(orderService.save(o), OrderDTO.class);
    }

    public OrderDTO findOne(Long id) {
        logger.info("Requested to find Order with id : {}", id);
        return mappingService.mapTo(orderService.findOne(id), OrderDTO.class);
    }

    public List<OrderDTO> findAll() {
        logger.info("Requested to find all Orders");
        return mappingService.mapTo(orderService.findAll(), OrderDTO.class);
    }    

    public void delete(Long id) {
        logger.info("Requested to delete Order with id : {}", id);
        orderService.delete(id);
    }

    @Override
    public List<OrderDTO> getOrdersByState(OrderStateDTO orderState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
