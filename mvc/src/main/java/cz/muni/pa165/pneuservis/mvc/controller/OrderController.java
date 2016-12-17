package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
import cz.muni.pa165.pneuservis.api.dto.TireDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.OrderFacade;
import cz.muni.pa165.pneuservis.api.facade.TireFacade;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public String createOrder(Model model) {
        OrderDTO order = new OrderDTO();
        order.setTire(new TireDTO());
        model.addAttribute("order", order);
        model.addAttribute("tireList", getTiresMap());
        return "orders/edit";
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrder(@Valid @ModelAttribute("order") OrderDTO order,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            UriComponentsBuilder uriBuilder) {

        logger.info("Saving Order: {}", order);

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            model.addAttribute("tireList", getTiresMap());
            return "orders/edit";
        }

        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        order.setId(null);
        order.setState(OrderStateDTO.RECEIVED);
        TireDTO tire = tireFacade.findOne(order.getTire().getId());
        order.setTire(tire);
        order.setPrice(tire.getPrice().multiply(new BigDecimal(order.getTireQuantity())));
//        order.setDateCreated(Calendar.getInstance().getTime());
        order.setUser(user);

        orderFacade.save(order);
        return "redirect:" + uriBuilder.path("/orders/").toUriString();
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        List<OrderDTO> orders = orderFacade.findByUser(user);
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public String listAll(Model model) {
        List<OrderDTO> orders;
        orders = orderFacade.findAll();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model,
                         UriComponentsBuilder uriBuilder,
                         @AuthenticationPrincipal UserDetails userDetails) {
        OrderDTO order = orderFacade.findOne(id);

        if (order != null && !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) &&
                !order.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("403");
        }

        model.addAttribute("order", order);
        return "orders/detail";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/finish/{id}", method = RequestMethod.GET)
    public String finishOrder(@PathVariable long id, Model model) {

        OrderDTO order;
        order = orderFacade.findOne(id);
        order.setState(OrderStateDTO.DONE);
        orderFacade.save(order);


        model.addAttribute("order", order);
        return "orders/detail";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public String cancelOrder(@PathVariable long id, Model model) {

        OrderDTO order;
        order = orderFacade.findOne(id);
        order.setState(OrderStateDTO.CANCELED);
        orderFacade.save(order);

        model.addAttribute("order", order);
        return "orders/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id, UriComponentsBuilder uriBuilder) {
        OrderDTO order = orderFacade.findOne(id);

        if (order != null && !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) &&
                !order.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("403");
        }
        orderFacade.delete(id);

        return "redirect:" + uriBuilder.path("/orders/").toUriString();
    }

    private Map<Long, String> getTiresMap() {
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
        return tireNames;
    }

}
