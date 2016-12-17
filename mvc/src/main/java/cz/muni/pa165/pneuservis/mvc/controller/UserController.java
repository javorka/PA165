package cz.muni.pa165.pneuservis.mvc.controller;

import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import cz.muni.pa165.pneuservis.mvc.form.UserEditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
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
    public String editSelf(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDTO user = userFacade.findByEmail(userDetails.getUsername());
        UserEditForm userForm = new UserEditForm();
        userForm.setEmail(user.getEmail());
        userForm.setName(user.getName());
        model.addAttribute("user", userForm);
        return "user/edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("edit")
    public String submitEdit(@AuthenticationPrincipal UserDetails userDetails,
                             @Valid @ModelAttribute("user") UserEditForm userForm,
                             BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }

            return "user/edit";
        }

        UserDTO user = userFacade.findByEmail(userDetails.getUsername());

        user.setName(userForm.getName());

        if (!userForm.getPassword().isEmpty())
            user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));

        model.addAttribute("user", userFacade.save(user));

        redirectAttributes.addFlashAttribute("alert_success", "User details were saved.");
        return "redirect:" + uriBuilder.path("/user/edit").toUriString();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("edit/{id}")
    public String editUser(@PathVariable long id, Model model,
                           UriComponentsBuilder uriBuilder) {
        UserDTO user = userFacade.findOne(id);
        if (user == null) {
            return "redirect:" + uriBuilder.path("/user/list").toUriString();
        }
        UserEditForm userForm = new UserEditForm();
        userForm.setEmail(user.getEmail());
        userForm.setName(user.getName());
        model.addAttribute("user", userForm);
        model.addAttribute("id", id);
        return "user/edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("edit/{id}")
    public String submitEdit(@PathVariable long id,
                             @Valid @ModelAttribute("user") UserEditForm userForm,
                             BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            model.addAttribute("id", id);
            return "user/edit";
        }

        UserDTO user = userFacade.findOne(id);
        if (user == null) {
            return "redirect:" + uriBuilder.path("/user/list").toUriString();
        }
        user.setName(userForm.getName());

        if (!userForm.getPassword().isEmpty())
            user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));

        model.addAttribute("user", userFacade.save(user));

        redirectAttributes.addFlashAttribute("alert_success", "User details were saved.");
        return "redirect:" + uriBuilder.path("/user/edit/{id}").buildAndExpand(id).encode().toUriString();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "delete/{id}")
    public String delete(@PathVariable long id,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        try {
            userFacade.delete(id);
            redirectAttributes.addFlashAttribute("alert_success", "User deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "User cannot be deleted.");
        }
        return "redirect:" + uriBuilder.path("/user/list").toUriString();
    }

}
