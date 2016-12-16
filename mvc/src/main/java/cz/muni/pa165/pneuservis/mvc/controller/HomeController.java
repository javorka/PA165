package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.RoleDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collections;

/**
 * @author Michal
 * @version 1.0
 * @since 12/15/2016
 */
@RequestMapping("/")
@Controller
public class HomeController {

    final static Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserFacade userFacade;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("user", userDetails == null ? "Anon" : userDetails.getUsername());
        return "home";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("login")
    public String loginGet() {
        return "login";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("signup")
    public String signUp(Model model) {
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("signup")
    public String submitSignUp(@Valid @ModelAttribute("user") UserDTO user,
                               BindingResult bindingResult,
                               UriComponentsBuilder uriBuilder,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "signup";
        }

        user.setId(null);
        user.setRoles(Collections.singletonList(RoleDTO.CUSTOMER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userFacade.save(user);
        redirectAttributes.addFlashAttribute("alert_success", "Registration complete, please sign in.");
        return "redirect:" + uriBuilder.path("/login").toUriString();
    }
}
