package cz.muni.pa165.pneuservis.service.impl;

import cz.muni.pa165.pneuservis.persistence.domain.AdditionalService;
import cz.muni.pa165.pneuservis.persistence.repository.AdditionalServiceRepository;
import cz.muni.pa165.pneuservis.service.AdditionalServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by peter on 11/18/16.
 */
@Service
@Transactional
public class AdditionalServiceServiceImpl implements AdditionalServiceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    AdditionalServiceRepository repository;

    @Override
    public AdditionalService save(AdditionalService service) {
        logger.info("Requested to save AdditionalService : {}", service);
        return repository.save(service);
    }

    @Override
    public AdditionalService findOne(Long id) {
        logger.info("Requested to find AdditionalService with id : {}", id);
        return repository.findOne(id);
    }

    @Override
    public List<AdditionalService> findByNameContaining(String name) {
        logger.info("Requested to find AdditionalServices by name : {}", name);
        return repository.findByNameContaining(name);
    }

    @Override
    public List<AdditionalService> findAll() {
        logger.info("Requested to find all AdditionalServices");
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        logger.info("Requested to delete AdditionalService with id: {} ", id);
        repository.delete(id);
    }
}
