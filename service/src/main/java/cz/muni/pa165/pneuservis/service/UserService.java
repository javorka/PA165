package cz.muni.pa165.pneuservis.service;

import cz.muni.pa165.pneuservis.persistence.domain.User;

import java.util.List;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
public interface UserService {
    User save(User user);

    User findOne(Long id);

    User findByEmail(String email);

    List<User> findAll();

    void delete(Long id);
}
