package cz.muni.pa165.pneuservis.facade;

import cz.muni.pa165.pneuservis.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michal Travnicek xtravni2
 */
@Service
public interface OrderFacade {
    OrderDTO save(OrderDTO dto);
    OrderDTO findOne(Long id);
    List<OrderDTO> findAll();
    void delete(Long id);
}
