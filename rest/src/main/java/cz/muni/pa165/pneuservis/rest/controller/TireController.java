package cz.muni.pa165.pneuservis.rest.controller;

import cz.muni.pa165.pneuservis.api.dto.TireDTO;
import cz.muni.pa165.pneuservis.api.dto.TireTypeDTO;
import cz.muni.pa165.pneuservis.api.facade.TireFacade;
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
@RequestMapping(value="/tires")
public class TireController extends BaseController {
    @Inject
    TireFacade tireFacade;

    @RequestMapping(value = "/all-tires", method = RequestMethod.GET, produces = "application/json")
    public List<TireDTO> findAll() {
        List<TireDTO> allTires = new ArrayList<TireDTO>();
        allTires.addAll(tireFacade.findAll());
        return allTires;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public TireDTO findOne(@PathVariable("id") Long id) {
        return tireFacade.findOne(id);
    }

    @RequestMapping(value = "/find-by-tire-type", method = RequestMethod.GET, produces = "application/json")
    public List<TireDTO> findByTireType(@RequestParam TireTypeDTO input) {
        TireTypeDTO tireTypeDTO = input;
        return tireFacade.findByTireType(tireTypeDTO);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> add(@RequestBody TireDTO input){
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
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tireFacade.delete(id);
        String response = new String("Tire with id "+id+" was deleted");
        return ResponseEntity.ok(response);
    }
}
