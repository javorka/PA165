package cz.muni.pa165.pneuservis.rest.controller;

import cz.muni.pa165.pneuservis.api.dto.TireDTO;
import cz.muni.pa165.pneuservis.api.dto.TireTypeDTO;
import cz.muni.pa165.pneuservis.api.facade.TireFacade;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Spisiak <spisiak@mail.muni.cz> on 13/12/2016.
 */

@RestController
@Api(value = "Tire REST controller", description = "Provides methods for retrieving data about tires in JSON format")
@RequestMapping(value="/tires")
public class TireController extends BaseController {
    @Inject
    TireFacade tireFacade;

    @RequestMapping(value = "/all-tires", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Returns all tires", notes = "Returns a complete list of tires in JSON format", response = TireDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of all tires", response = TireDTO.class),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service temporarily unavailable")}
    )
    public List<TireDTO> findAll() {
        List<TireDTO> allTires = new ArrayList<TireDTO>();
        allTires.addAll(tireFacade.findAll());
        return allTires;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Returns a tire with specified id", notes = "Returns a tire with the id in JSON format", response = TireDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of a tire with the id", response = TireDTO.class),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service temporarily unavailable")}
    )
    public TireDTO findOne(
            @ApiParam(name = "ID", value = "ID number of type Long", required = true)
            @PathVariable("id") Long id) {
        return tireFacade.findOne(id);
    }

    @RequestMapping(value = "/find-by-tire-type", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Returns tires with specified tire type", notes = "Returns a complete list of tires with the tire type in JSON format", response = TireDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of all tires with specified tire type", response = TireDTO.class),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service temporarily unavailable")}
    )
    public List<TireDTO> findByTireType(
            @ApiParam(name = "Tire type", value = "One of the TireTypeDTO enum values", required = true)
            @RequestParam TireTypeDTO input) {
        TireTypeDTO tireTypeDTO = input;
        return tireFacade.findByTireType(tireTypeDTO);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "Adds tire to a database", notes = "Creates a tire and sends an acknowledgement")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful tire addition"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service temporarily unavailable")}
    )
    public ResponseEntity<?> add(
            @ApiParam(name = "Tire", value = "Tire to be added in JSON format", required = true)
            @RequestBody TireDTO input){
        TireDTO tireDTO = new TireDTO();
        tireDTO.setName(input.getName());
        tireDTO.setTireType(input.getTireType());
        tireDTO.setSize(input.getSize());
        tireDTO.setManufacturer(input.getManufacturer());
        tireDTO.setVehicleType(input.getVehicleType());
        tireDTO.setPrice(input.getPrice());
        TireDTO result = tireFacade.save(tireDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body("Tire added: "+result);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes a tire", notes = "Deletes a tire specified by an ID of type Long")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful tire deletion"),
            @ApiResponse(code = 404, message = "Tire with specified id not found"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service temporarily unavailable")}
    )
    public ResponseEntity<?> delete(
            @ApiParam(name = "ID", value = "ID number of type Long", required = true)
            @PathVariable Long id) {
        if (tireFacade.findOne(id) != null) {
            tireFacade.delete(id);
            String response = new String("Tire with id " + id + " was deleted");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
