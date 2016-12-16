package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.TireDTO;
import cz.muni.pa165.pneuservis.api.facade.TireFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Martin Spisiak <spisiak@mail.muni.cz> on 16/12/2016.
 */
@RequestMapping("/tires")
@Controller
public class TireController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private TireFacade tireFacade;

    @GetMapping("/")
    public String getAllTires(Model model){
        logger.info("Showing all tires.");
        model.addAttribute("tires", tireFacade.findAll());
        return "tires/list";
    }

    @GetMapping("/new")
    public String addTire(Model model) {
        model.addAttribute("tire", new TireDTO());
        return "tires/edit";
    }

    @GetMapping("/edit/{id}")
    public String editAdditionalService(@PathVariable Long id, Model model) {
        model.addAttribute("tire", tireFacade.findOne(id));
        return "tires/edit";
    }

    @PostMapping("/save")
    public String submitEdit(@Valid @ModelAttribute("tire") TireDTO tireDTO,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {

        logger.info("Saving AdditionalServiceDTO: {}", tireDTO);
        if (bindingResult.hasErrors()) {
            return "tires/edit";
        }
        tireFacade.save(tireDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Tire details were saved.");
        return "redirect:" + uriBuilder.path("/tires/").toUriString();
//        model.addAttribute("tire", tireFacade.save(tire));
//
//        redirectAttributes.addFlashAttribute("alert_success", "Tire details were saved.");
//        return "redirect:" + uriBuilder.path("/tires/edit/{id}").buildAndExpand(id).encode().toUriString();
    }

    @PostMapping(value="/delete/{id}")
    public String delete(@PathVariable long id,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        logger.info("Deleting tire with id: {}", id);
        TireDTO tireDTO = tireFacade.findOne(id);
        try {
            tireFacade.delete(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Tire " + tireDTO.getName() + " cannot be deleted.");
            return "redirect:" + uriBuilder.path("/tires/").toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success", "Tire " + tireDTO.getName() + " deleted.");
        return "redirect:" + uriBuilder.path("/tires/").toUriString();
    }
}
