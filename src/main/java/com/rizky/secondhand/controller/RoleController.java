package com.rizky.secondhand.controller;

import com.rizky.secondhand.entity.Role;
import com.rizky.secondhand.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/createnewrole")
    public Role createNewRole(@RequestBody Role role) {
    return roleService.createNewRole(role);
    }
}
