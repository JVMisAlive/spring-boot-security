package com.web.controller;

import com.web.model.Role;
import com.web.model.User;
import com.web.service.RoleService;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "users";
    }

    @GetMapping("/admin/add")
    public String addUser(Model model) {
        model.addAttribute("addUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "userAdd";
    }

    @PostMapping("/admin/add")
    public ModelAndView createUser(@ModelAttribute("userAdd") User user, @RequestParam("addRole") Set<String> editRole) {
        user.setRoles(roleService.getRoleSet(editRole));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.saveUser(user);
        return modelAndView;
    }

    @GetMapping("/admin/edit/{id}")
    public ModelAndView editPage(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userEdit");
        modelAndView.addObject("userEdit", user);
        modelAndView.addObject("allRoles", roleService.getAllRoles());
        return modelAndView;
    }

    @PostMapping("/admin/edit/{id}")
    public ModelAndView editUser(@ModelAttribute("userEdit") User user, @RequestParam("editRole") Set<String> editRole) {
        user.setRoles(roleService.getRoleSet(editRole));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.edit(user);
        return modelAndView;
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
