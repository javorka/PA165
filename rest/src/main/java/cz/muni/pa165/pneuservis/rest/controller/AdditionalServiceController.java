package cz.muni.pa165.pneuservis.rest.controller;

import com.fasterxml.jackson.databind.deser.Deserializers;
import cz.muni.pa165.pneuservis.api.dto.AdditionalServiceDTO;
import cz.muni.pa165.pneuservis.api.facade.AdditionalServiceFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by peter on 12/13/16.
 */
@RestController
@RequestMapping("/additional-service")
@Api(value = "Additional service REST controller", description = "Provides methods for manipulating with Additional service entity")
public class AdditionalServiceController extends BaseController {

    @Autowired
    AdditionalServiceFacade facade;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Returns all Additional services")
    public List<AdditionalServiceDTO> getAllAdditionalServices() {
        logger.info("Request to find all Additional services");
        return facade.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns an Additional service by given ID")
    public AdditionalServiceDTO getAdditionalService(@PathVariable Long id) {
        logger.info("Request to find an Additional service with id: {}", id);
        return facade.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "Create an Additional service")
    public AdditionalServiceDTO createAdditionalService(@RequestBody AdditionalServiceDTO dto) {
        logger.info("Request to create an Additional service: {}", dto);
        return facade.save(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update an Additional service")
    public AdditionalServiceDTO updateAdditionalService(@PathVariable Long id, @RequestBody AdditionalServiceDTO dto) {
        logger.info("Request to update an Additional service: {}", dto);
        dto.setId(id);
        return facade.save(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete an Additional service")
    public ResponseEntity deleteAdditionalService(@PathVariable Long id) {
        logger.info("Request to delete an Additional service with id: {}", id);
        try {
            facade.delete(id);
            return ResponseEntity.ok("Additional service with id " + id + " was successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
