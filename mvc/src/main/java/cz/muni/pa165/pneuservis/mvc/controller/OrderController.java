package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
import cz.muni.pa165.pneuservis.api.facade.OrderFacade;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Michal Travnicek
 */
@RequestMapping("/orders")
@Controller
public class OrderController {

    @Autowired
    private OrderFacade orderFacade;   
   
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createOrder() {                

        return "orders/create";

    }

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
            order = null;//?
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
            order = null;
        }
                        
        model.addAttribute("order", order);        
        return "orders/detail";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteOrder(@PathVariable long id, UriComponentsBuilder uriBuilder) {

        try {
            orderFacade.delete(id);

        } catch (Exception e) {
        }
                        
         return "redirect:" + uriBuilder.path("/orders/").toUriString();
    }

}
