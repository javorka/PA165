package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.facade.AdditionalServiceFacade;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Created by xjavorka on 14.12.16.
 */
@Controller
@RequestMapping("/additionalService")
public class AdditionalServiceController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdditionalServiceFacade facade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String findAll(Model model) {
        logger.info("Showing all additional services");
        model.addAttribute("additionalServices", facade.findAll());
        return "additionalService/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id, UriComponentsBuilder uriBuilder) {
        logger.info("Deleting additional service with id: {}", id);
        facade.delete(id);
        return "redirect:" + uriBuilder.path("/additionalService/list").toUriString();
    }
}
