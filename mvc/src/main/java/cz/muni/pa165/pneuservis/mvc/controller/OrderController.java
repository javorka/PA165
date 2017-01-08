package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.AdditionalServiceFacade;
import cz.muni.pa165.pneuservis.api.facade.OrderFacade;
import cz.muni.pa165.pneuservis.api.facade.TireFacade;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import cz.muni.pa165.pneuservis.mvc.form.NewOrderForm;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: additional service in order creation
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

    @Autowired
    private AdditionalServiceFacade additionalServiceFacade;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createOrder(Model model) {
        NewOrderForm orderForm = new NewOrderForm();
        model.addAttribute("orderForm", orderForm);
        model.addAttribute("additionalServices", additionalServiceFacade.findAll());
        model.addAttribute("tires", tireFacade.findAll());
        return "orders/edit";
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
    public String createOrderWithTire(@PathVariable long id, Model model) {
        NewOrderForm orderForm = new NewOrderForm();
        orderForm.setTireId(id);
        model.addAttribute("orderForm", orderForm);
        model.addAttribute("additionalServices", additionalServiceFacade.findAll());
        model.addAttribute("tires", tireFacade.findAll());
        return "orders/edit";
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrder(@Valid @ModelAttribute("orderForm") NewOrderForm orderForm,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            UriComponentsBuilder uriBuilder) {

        logger.info("Saving Order: {}", orderForm);

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            model.addAttribute("additionalServices", additionalServiceFacade.findAll());
            model.addAttribute("tires", tireFacade.findAll());
            return "orders/edit";
        }

        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        OrderDTO order = new OrderDTO();
        order.setTire(tireFacade.findOne(orderForm.getTireId()));
        order.setTireQuantity(Integer.valueOf(orderForm.getTireQuantity()));
        order.setAddress(orderForm.getAddress());
        order.setPhone(orderForm.getPhone());
        order.setAdditionalServices(orderForm.getAdditionalServices().stream().map(i -> additionalServiceFacade.findOne(i)).collect(Collectors.toList()));
        order.setUser(user);

        OrderDTO save = orderFacade.receive(order);

        redirectAttributes.addFlashAttribute("alert_success", "Order created.");
        return "redirect:" + uriBuilder.path("/orders/detail/{id}").buildAndExpand(save.getId()).encode().toUriString();
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
    public String deleteOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id, UriComponentsBuilder uriBuilder,
                              RedirectAttributes redirectAttributes) {
        OrderDTO order = orderFacade.findOne(id);

        if (order != null && !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) &&
                !order.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("403");
        }
        orderFacade.delete(id);

        redirectAttributes.addFlashAttribute("alert_success", "Order deleted.");
        return "redirect:" + uriBuilder.path("/orders/").toUriString();
    }

}
