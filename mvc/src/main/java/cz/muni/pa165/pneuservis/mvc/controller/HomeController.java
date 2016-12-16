package cz.muni.pa165.pneuservis.mvc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Michal
 * @version 1.0
 * @since 12/15/2016
 */
@RequestMapping("/")
@Controller
public class HomeController {

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

//    @PreAuthorize("isAnonymous()")
//    @GetMapping("signup")
//    public String signUp() {
//        return "signup";
//    }
//
//    @PreAuthorize("isAnonymous()")
//    @PostMapping("signup")
//    public String submitSignUp() {
//        // bCryptPasswordEncoder.encode(userRegistrationForm.getPassword())
//        return "lol";
//    }
}
