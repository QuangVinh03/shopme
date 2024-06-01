package com.shopme.admin.user;

import com.shopme.core.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.shopme.core.entity.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("listUsers", users);
        return "users";
    }

    @GetMapping("users/new")
    public String createNewUser(Model model) {
        List<Role> roles = userService.findAllRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roles);
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }

    @PostMapping("users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        redirectAttributes.addFlashAttribute("message","The user has been successfully saved.");
        return "redirect:/users";
    }

    @GetMapping("users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.get(id);
            List<Role> roles = userService.findAllRoles();
            model.addAttribute("user", user);
            model.addAttribute("listRoles", roles);
            model.addAttribute("pageTitle", "Edit User By Id "+id);
            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }
    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message","The user with id: "+id+" has been deleted.");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }
//    @GetMapping("users/{id}/enable/{status}")
//    public String updateEnableStatus(@PathVariable(name = "id") Integer id,@PathVariable(name = "status") Boolean enabled
//            ,RedirectAttributes redirectAttributes) throws UserNotFoundException {
//        userService.updateUserEnabledStatus(id, enabled);
//        String status = enabled ? "enabled" : "disabled";
//        String message = "The user with id: " + id + " has been " + status;
//        redirectAttributes.addFlashAttribute("message", message);
//        return "redirect:/users";
//    }

}
