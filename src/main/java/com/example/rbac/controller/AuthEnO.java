package com.example.rbac.controller;

import com.example.rbac.models.AssignRole;
import com.example.rbac.models.StructureRole;
import com.example.rbac.models.User;
import com.example.rbac.requests.UserRequest;
import com.example.rbac.services.ISecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthEnO {


    @Autowired
    ISecurity securityService;


    @PostMapping("/signup")
    public String user(@RequestBody UserRequest user) {
         securityService.signup(new User(user.getUsername(), user.getPassword(), user.getEmail()));
         return "User signed up successfully";
    }

    @PostMapping("/assign-role")
    public String admin(@RequestBody AssignRole assignRole) {
        securityService.assignRoles(assignRole);
        return "Roles are assigned successfully";
    }

    @GetMapping("/users")
    public List<User> getUsers(){

       return securityService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<StructureRole> getAllRoles(){

        return securityService.getAllRoles();
    }

    @GetMapping("/")
    public String getWelcome(){

        return "Welcome";
    }

    @PostMapping("/create-role")
    @PreAuthorize("hasAuthority('Global-admin')")
    public String createRole(@RequestBody StructureRole role){
        securityService.createRole(role);
        return "Role created successfully";
    }




}
