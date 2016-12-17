package cz.muni.pa165.pneuservis.api.facade;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;

import java.util.List;

/**
 * @author Michal Travnicek xtravni2
 */
public interface OrderFacade {
    /**
     * Saves a given OrderDTO. Use the returned instance for further operations
     * as the save operation might have changed the entity instance completely.
     *
     * @param dto OrderDTO to save
     * @return the saved Order
     */
    OrderDTO save(OrderDTO dto);

    /**
     * Retrieves an OrderDTO by its id.
     *
     * @param id must not be null.
     * @return the OrderDTO with the given id or null if none found
     */
    OrderDTO findOne(Long id);

    /**
     * Returns all instances of the OrderDTO.
     *
     * @return all entities
     */
    List<OrderDTO> findAll();

    /**
     * Deletes the OrderDTO with the given id.
     *
     * @param id - must not be null.
     */
    void delete(Long id);

    List<OrderDTO> findByUser(UserDTO user);
}
