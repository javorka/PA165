/*
 * To change this template file, choose Tools | Templates
 */
package cz.muni.pa165.pneuservis.persistence.repository;

import cz.muni.pa165.pneuservis.persistence.config.Loggable;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Michal
 */
@Component
public class UserClient {

    @Inject
    private UserRepository repository;

    @Loggable
    public void businessMethod() {

        List<User> people = repository.findAll();
    }

    public UserRepository getRepository() {
        return repository;
    }
}
