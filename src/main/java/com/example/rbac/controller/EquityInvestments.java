package com.example.rbac.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquityInvestments {


    @GetMapping("/read-structure-data")
    @PreAuthorize("hasAnyAuthority('X-admin','Y-admin')")
    public String readStructDataXY(){
        return "X-details;  Y-details";
    }

    @GetMapping("/read-structure-data-x")
    @PreAuthorize("hasAuthority('X-read')")
    public String readStructDataX(){
        return "X-details";
    }
}
