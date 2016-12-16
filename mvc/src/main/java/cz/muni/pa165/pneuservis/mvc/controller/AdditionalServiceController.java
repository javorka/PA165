package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.AdditionalServiceDTO;
import cz.muni.pa165.pneuservis.api.facade.AdditionalServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;


/**
 * Created by xjavorka on 14.12.16.
 */
@Controller
@RequestMapping("/additionalService")
public class AdditionalServiceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdditionalServiceFacade facade;

    @GetMapping("/")
    public String findAllAdditionalServices(Model model) {
        logger.info("Showing all additional services");
        model.addAttribute("additionalServices", facade.findAll());
        return "additionalService/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newAdditionalService(Model model) {
        model.addAttribute("additionalService", new AdditionalServiceDTO());
        return "additionalService/edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editAdditionalService(@PathVariable Long id, Model model) {
        model.addAttribute("additionalService", facade.findOne(id));
        return "additionalService/edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public String saveAdditionalService(@Valid @ModelAttribute("additionalService") AdditionalServiceDTO dto,
                                        BindingResult bindingResult,
                                        Model model,
                                        RedirectAttributes redirectAttributes,
                                        UriComponentsBuilder uriBuilder) {
        logger.info("Saving AdditionalServiceDTO: {}", dto);
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder("Validation failed for fields: ");
            bindingResult.getFieldErrors().forEach(fieldError -> builder.append(fieldError.getField()).append(", "));
            builder.setLength(builder.length() - 2);

            model.addAttribute("alert_danger", builder.toString());
            return "additionalService/edit";
        }
        facade.save(dto);
        redirectAttributes.addFlashAttribute("alert_success", "Additional service was saved.");
        return "redirect:" + uriBuilder.path("/additionalService/").toUriString();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        logger.info("Deleting additional service with id: {}", id);
        try {
            facade.delete(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "ERROR! Additional service cannot be deleted.");
            return "redirect:" + uriBuilder.path("/additionalService/").toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success", "Additional service was successfully deleted.");
        return "redirect:" + uriBuilder.path("/additionalService/").toUriString();
    }
}
