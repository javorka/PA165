package cz.muni.pa165.pneuservis.service.impl;

import cz.muni.pa165.pneuservis.api.dto.AdditionalServiceDTO;
import cz.muni.pa165.pneuservis.api.facade.AdditionalServiceFacade;
import cz.muni.pa165.pneuservis.persistence.domain.AdditionalService;
import cz.muni.pa165.pneuservis.service.AdditionalServiceService;
import cz.muni.pa165.pneuservis.service.BeanMappingService;
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
public class AdditionalServiceFacadeImpl implements AdditionalServiceFacade {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public AdditionalServiceService additionalServiceService;
    @Inject
    public BeanMappingService mappingService;

    @Override
    public AdditionalServiceDTO save(AdditionalServiceDTO dto) {
        logger.info("Requested to save AdditionalService : {}", dto);
        AdditionalService service = mappingService.mapTo(dto, AdditionalService.class);
        return mappingService.mapTo(additionalServiceService.save(service), AdditionalServiceDTO.class);
    }

    @Override
    public AdditionalServiceDTO findOne(Long id) {
        logger.info("Requested to find AdditionalService with id : {}", id);
        return mappingService.mapTo(additionalServiceService.findOne(id), AdditionalServiceDTO.class);
    }

    @Override
    public List<AdditionalServiceDTO> findAll() {
        logger.info("Requested to find all AdditionalServices");
        return mappingService.mapTo(additionalServiceService.findAll(), AdditionalServiceDTO.class);
    }

    @Override
    public List<AdditionalServiceDTO> findByNameContaining(String name) {
        logger.info("Requested to find all AdditionalServices containing name : {}", name);
        return mappingService.mapTo(additionalServiceService.findByNameContaining(name), AdditionalServiceDTO.class);
    }

    @Override
    public void delete(Long id) {
        logger.info("Requested to delete AdditionalService with id : {}", id);
        additionalServiceService.delete(id);
    }
}
