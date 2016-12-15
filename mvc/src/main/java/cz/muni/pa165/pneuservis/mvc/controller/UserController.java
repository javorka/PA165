package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("list")
    public String getAll(Model model) {
        List<UserDTO> users = userFacade.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/")
    public String currentUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("user", userDetails == null ? "Anon" : userDetails.getUsername());
        return "user/view";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("edit")
    public String editUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("edit")
    public String submitEdit(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("user") UserDTO userDTO, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        UserDTO user = userFacade.findByEmail(userDetails.getUsername());

        if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }

        model.addAttribute("user", userFacade.save(user));

        redirectAttributes.addFlashAttribute("alert_success", "User details were saved.");
        return "redirect:" + uriBuilder.path("/user/edit").toUriString();
    }

    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("delete/{id}")
    public String delete(@PathVariable long id) {
        userFacade.delete(id);
        return "{response: 'Success'}";
    }

}
