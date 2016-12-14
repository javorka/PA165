package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.RoleDTO;
import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @ResponseBody
    @RequestMapping(value="createRandom", method = RequestMethod.GET)
    public String createRandomPeople() {
        UserDTO admin = new UserDTO();

        admin.setId(1L);
        admin.setEmail("admin@admin.com");
        admin.setName("Admin");
        admin.setPassword(bCryptPasswordEncoder.encode("password"));
        admin.setRoles(Collections.singletonList(RoleDTO.ADMIN));

        userFacade.save(admin);

        return "lololo";
    }

    @ResponseBody
    @GetMapping("getAll")
    public String getAll() {
        String output=  "";
        List<UserDTO> all = userFacade.findAll();
        for (UserDTO userDTO : all) {
            output += userDTO.getEmail() + " ";
        }
        return output;
    }

    @ResponseBody
    @RequestMapping(value="getCurrent", method = RequestMethod.GET)
    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping(value="login", method = RequestMethod.GET)
    public String loginGet() {
        return "login";
    }

    @GetMapping(value="signup")
    public String signUp() {
        return "lol";
    }

    @PostMapping
    public String submitSignUp() {
        // bCryptPasswordEncoder.encode(userRegistrationForm.getPassword())
        return "lol";
    }


}
