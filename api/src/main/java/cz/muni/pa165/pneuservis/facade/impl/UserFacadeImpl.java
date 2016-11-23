package cz.muni.pa165.pneuservis.facade.impl;

import cz.muni.pa165.pneuservis.dto.UserDTO;
import cz.muni.pa165.pneuservis.facade.UserFacade;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.service.BeanMappingService;
import cz.muni.pa165.pneuservis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
public class UserFacadeImpl implements UserFacade {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private UserService service;

    @Inject
    public BeanMappingService mapper;

    @Override
    public UserDTO save(UserDTO user) {
        logger.info("Requested to save User : {}", user);
        User u = mapper.mapTo(user, User.class);
        return mapper.mapTo(service.save(u), UserDTO.class);
    }

    @Override
    public UserDTO findOne(Long id) {
        logger.info("Requested to find User with id : {}", id);
        return mapper.mapTo(service.findOne(id), UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        logger.info("Requested to find AdditionalServices by email : {}", email);
        return mapper.mapTo(service.findByEmail(email), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        logger.info("Requested to find all Users");
        return mapper.mapTo(service.findAll(), UserDTO.class);
    }

    @Override
    public void delete(Long id) {
        logger.info("Requested to delete User with id: {} ", id);
        service.delete(id);
    }
}
