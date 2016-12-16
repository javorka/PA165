package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
import cz.muni.pa165.pneuservis.api.dto.TireDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.OrderFacade;
import cz.muni.pa165.pneuservis.api.facade.TireFacade;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Michal Travnicek
 */
@RequestMapping("/orders")
@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderFacade orderFacade;
    
    @Autowired
    private UserFacade userFacade;
    
    @Autowired
    private TireFacade tireFacade;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createOrder(@AuthenticationPrincipal UserDetails userDetails, Model model) {
     
        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        OrderDTO order = new OrderDTO();
        List tires = tireFacade.findAll();
        
        Map<Long,String> tireNames = new LinkedHashMap<>();
        for (Iterator it = tires.iterator(); it.hasNext();) {
            TireDTO tire = (TireDTO) it.next();
            String temp = tire.getName()+  
                ", type=" + tire.getTireType() +
                ", size='" + tire.getSize()+ '\'' +
                ", manufacturer='" + tire.getManufacturer() + '\'' +
                ", vehicle='" + tire.getVehicleType() + '\'' +
                ", price=" + tire.getPrice();
            tireNames.put(tire.getId(), temp);
                    
          }
        
        
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        model.addAttribute("tires", tires);
        model.addAttribute("tireList", tireNames);
        return "orders/edit";

    }
    

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrder(@ModelAttribute("order") OrderDTO order,
            @ModelAttribute("user") UserDTO user,
            BindingResult bindingResult,
            Model model,
            UriComponentsBuilder uriBuilder) {

        logger.info("Saving Order: {}", order);
//        if (bindingResult.hasErrors()) {
//            return "orders/edit";
//        }
        try {
            order.setUser(user);
            orderFacade.save(order);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return "redirect:" + uriBuilder.path("/orders/").toUriString();
    }
//
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public String saveOrder(@Valid @ModelAttribute("order") OrderDTO order,
//            BindingResult bindingResult,
//            Model model,
//            UriComponentsBuilder uriBuilder) {
//
//        logger.info("Saving Order: {}", order);
//        if (bindingResult.hasErrors()) {
//            return "orders/edit";
//        }
//        try {
//            orderFacade.save(order);
//        } catch (Exception e) {
//            logger.error(e.toString());
//        }
//        return "redirect:" + uriBuilder.path("/orders/").toUriString();
//    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listOrders(Model model) {
        List<OrderDTO> orders;
        orders = new ArrayList<>();
        orders = orderFacade.findAll();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {

        OrderDTO order;
        try {

            order = orderFacade.findOne(id);

        } catch (Exception e) {
            logger.error(e.toString());
            order = null;
        }
        model.addAttribute("order", order);
        return "orders/detail";
    }

    @RequestMapping(value = "/finish/{id}", method = RequestMethod.GET)
    public String finishOrder(@PathVariable long id, Model model) {

        OrderDTO order;
        try {
            order = orderFacade.findOne(id);
            order.setState(OrderStateDTO.DONE);
            orderFacade.save(order);

        } catch (Exception e) {
            logger.error(e.toString());
            order = null;
        }

        model.addAttribute("order", order);
        return "orders/detail";
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public String cancelOrder(@PathVariable long id, Model model) {

        OrderDTO order;
        try {
            order = orderFacade.findOne(id);
            order.setState(OrderStateDTO.CANCELED);
            orderFacade.save(order);

        } catch (Exception e) {
            logger.error(e.toString());
            order = null;
        }

        model.addAttribute("order", order);
        return "orders/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteOrder(@PathVariable long id, UriComponentsBuilder uriBuilder) {

        try {
            orderFacade.delete(id);

        } catch (Exception e) {
            logger.error(e.toString());
        }

        return "redirect:" + uriBuilder.path("/orders/").toUriString();
    }

}
