package cz.muni.pa165.pneuservis.rest.controller;

import cz.muni.pa165.pneuservis.api.dto.AdditionalServiceDTO;
import cz.muni.pa165.pneuservis.api.facade.AdditionalServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by peter on 12/13/16.
 */
@RestController
@RequestMapping("/additional-service")
public class AdditionalServiceController {
    @Autowired
    AdditionalServiceFacade facade;

    @RequestMapping(method = RequestMethod.GET)
    public List<AdditionalServiceDTO> getAllAdditionalServices() {
        return facade.findAll();
    }
}
