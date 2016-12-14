package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
import cz.muni.pa165.pneuservis.api.dto.RoleDTO;
import cz.muni.pa165.pneuservis.api.dto.TireDTO;
import cz.muni.pa165.pneuservis.api.dto.TireTypeDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.OrderFacade;
import cz.muni.pa165.pneuservis.api.dto.OrderStateDTO;
//import cz.muni.pa165.pneuservis.persistence.enums.OrderState;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import org.springframework.ui.Model;

/**
 * @author Michal Travnicek
 */
@RequestMapping("/order")
@Controller
public class OrderController {

    @Autowired
    private OrderFacade orderFacade;    

    @ResponseBody
    @RequestMapping(value="createOrder", method = RequestMethod.GET)
    public String createOrder() {
        
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("customer1@mail.muni.cz");
        userDTO.setName("Customer 1");
        userDTO.setPassword("cGFzc3dvcmQ=");
        userDTO.setRoles(Collections.singletonList(RoleDTO.CUSTOMER));

        TireDTO tireDTO = new TireDTO();
        tireDTO.setName("Pneumatika 3000");
        tireDTO.setManufacturer("Michelin");
        tireDTO.setSize("21");
        tireDTO.setTireType(TireTypeDTO.AllSeason_Passenger);
        tireDTO.setPrice(new BigDecimal("3000"));
        tireDTO.setVehicleType("velke modre");
                
        OrderDTO order = new OrderDTO();
        order = new OrderDTO();
        order.setId(1L);
        order.setUser(userDTO);
        order.setAddress("Botanicka 68, 60200 Brno");
        order.setPhone("+420 156 123 749");
        order.setPrice(new BigDecimal(3000));
        order.setTire(tireDTO);
        order.setTireQuantity(4);
        order.setDateCreated(Calendar.getInstance().getTime());
        order.setState(OrderStateDTO.RECEIVED);

        return "order/created";
    }
    
    @RequestMapping(value = "/list/{filter}", method = RequestMethod.GET)
    public String list(@PathVariable String filter, Model model) {
        List<OrderDTO> orders;
        orders = new ArrayList<>();
        switch (filter) {
            case "all":
                orders = orderFacade.findAll();
                break;
            case "received":
                orders = orderFacade.getOrdersByState(OrderStateDTO.RECEIVED);
                break;
//            case "canceled":
//                orders = orderFacade.getOrdersByState(OrderState.CANCELED);
//                break;
//            case "done":
//                orders = orderFacade.getOrdersByState(OrderState.DONE);
//                break;
//            case "unprocessed":
//                orders = new ArrayList<>();
//                orders.addAll(orderFacade.getOrdersByState(OrderState.RECEIVED));
//                orders.addAll(orderFacade.getOrdersByState(OrderState.SHIPPED));
//                break;
            default:
                orders = new ArrayList<>();
                model.addAttribute("alert_danger", "Unknown filter " + filter);
        }
        model.addAttribute("orders", orders);
        return "order/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        //model.addAttribute("totalPrice", otp.getPrice());
        //model.addAttribute("order", otp.getOrder());
        return "order/detail";
    }


}
