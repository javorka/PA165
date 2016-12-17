package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
import cz.muni.pa165.pneuservis.api.dto.TireDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.OrderFacade;
import cz.muni.pa165.pneuservis.api.facade.TireFacade;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import cz.muni.pa165.pneuservis.mvc.form.NewOrderForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;

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

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createOrder(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        List tires = tireFacade.findAll();

        Map<Long, String> tireNames = new LinkedHashMap<>();
        for (Iterator it = tires.iterator(); it.hasNext(); ) {
            TireDTO tire = (TireDTO) it.next();
            String temp = tire.getName() +
                    ", type=" + tire.getTireType() +
                    ", size='" + tire.getSize() + '\'' +
                    ", manufacturer='" + tire.getManufacturer() + '\'' +
                    ", vehicle='" + tire.getVehicleType() + '\'' +
                    ", price=" + tire.getPrice();
            tireNames.put(tire.getId(), temp);

        }

        NewOrderForm orderForm = new NewOrderForm();
        orderForm.setOrder(new OrderDTO());
        model.addAttribute("orderForm", orderForm);
        model.addAttribute("user", user);
        model.addAttribute("tireList", tireNames);
        return "orders/edit";
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrder(@ModelAttribute("orderForm") NewOrderForm orderForm,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model,
                            UriComponentsBuilder uriBuilder) {

        logger.info("Saving Order: {}", orderForm);

        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        OrderDTO order = orderForm.getOrder();
        order.setId(null);
        order.setState(OrderStateDTO.RECEIVED);
        TireDTO tire = tireFacade.findOne(orderForm.getTireId());
        order.setTire(tire);
        order.setPrice(tire.getPrice().multiply(new BigDecimal(order.getTireQuantity())));
        order.setDateCreated(Calendar.getInstance().getTime());
        order.setUser(user);

        System.out.println(order);

        orderFacade.save(order);
        return "redirect:" + uriBuilder.path("/orders/").toUriString();
    }

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
